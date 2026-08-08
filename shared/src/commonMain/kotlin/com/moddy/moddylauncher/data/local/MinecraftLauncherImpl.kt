package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.LauncherPaths
import com.moddy.moddylauncher.common.MemoryRam
import com.moddy.moddylauncher.common.isArgAllowed
import com.moddy.moddylauncher.common.resolveArgument
import com.moddy.moddylauncher.common.resolveJVMArgument
import com.moddy.moddylauncher.database.instance.InstanceData
import com.moddy.moddylauncher.database.user.UserDTO
import com.moddy.moddylauncher.database.user.UserData
import com.moddy.moddylauncher.domain.version.DefaultUserJvm
import com.moddy.moddylauncher.domain.version.VersionManifest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import java.io.File

class MinecraftLauncherImpl(
    private val database: UserDTO,
) : MinecraftLauncher {
    override suspend fun launch(
        version: VersionManifest,
        instance: InstanceData,
        jre: File,
        libraries: List<Pair<String, File>>
    ) {

        val gameArgs = version.arguments?.let { arguments ->
            buildGameArgs(
                args = arguments.game,
                assetIndex = version.assetIndex.id,
                versionId = version.id,
                versionType = version.type,
                directory = instance.id.toString(),
                width = instance.width.toString().takeIf { it != "0" }.orEmpty(),
                height = instance.height.toString().takeIf { it != "0" }.orEmpty(),
            )
        }

        val jvmArgs = version.arguments?.let {
            buildJVMArgs(
                args = it.jvm,
                launcherName = "ModdyLauncher",
                launcherVersion = "1.0.0"
            )
        }

        val classPath = buildClasspath(
            libraries = libraries,
            versionId = version.id
        )

        val command = mutableListOf<String>()

        // Cambiar por un ejecutable real en tu Dispositivo
        command.add(jre.absolutePath)

        command.addAll(instance.JVMARGS.trim().split(Regex("\\s+")))
        if (jvmArgs != null) {
            command.addAll(jvmArgs)
        }

        command.add("-cp")
        command.add(classPath)

        command.add(version.mainClass)

        if (gameArgs != null) {
            command.addAll(gameArgs)
        }

        println(command)

        withContext(Dispatchers.IO) {
            ProcessBuilder(command)
                .directory(LauncherPaths.newProfile(instance.id.toString()).root)
                .inheritIO()
                .start()
        }
    }

    override fun buildDefaultJvmArgs(args: List<DefaultUserJvm>, minMem: MemoryRam, maxMem: MemoryRam): List<String> {

        val newList = args.mapNotNull {
            if (isArgAllowed(it)) it.value else null
        }.flatten().toMutableList()

        newList.remove("-Xms2G")
        newList.remove("-Xmx4G")

        newList.addFirst("-Xmx${maxMem.name.reversed()}")
        newList.addFirst("-Xms${minMem.name.reversed()}")

        return newList
    }

    override fun buildGameArgs(
        args: List<JsonElement>,
        assetIndex: String,
        versionId: String,
        directory: String,
        versionType: String,
        width: String,
        height: String,
    ): List<String> {

        val user = database.getUser()

        print("USER: $user")

        val ignoredArgs = setOf(
            "--demo",
            "--quickPlayPath",
            "--quickPlaySingleplayer",
            "--quickPlayMultiplayer",
            "--quickPlayRealms",
            "\${quickPlayPath}",
            "\${quickPlaySingleplayer}",
            "\${quickPlayMultiplayer}",
            "\${quickPlayRealms}"
        )

        return args
            .filter { element ->
                element is JsonPrimitive ||
                        element is JsonObject && isArgAllowed(element)
            }
            .flatMap { element ->
                val values = when (element) {
                    is JsonPrimitive -> listOf(element.content)

                    is JsonObject -> when (val value = element["value"]) {
                        is JsonPrimitive -> listOf(value.content)
                        is JsonArray -> value
                            .filterIsInstance<JsonPrimitive>()
                            .map { it.content }

                        else -> emptyList()
                    }

                    else -> emptyList()
                }

                values
                    .filterNot { it in ignoredArgs }
                    .map {
                        resolveArgument(
                            it,
                            versionId = versionId,
                            directory = LauncherPaths.newProfile(directory).root.absolutePath,
                            assetIndex = assetIndex,
                            userData = user ?: UserData("NO NAMED"),
                            versionType = versionType,
                            width = width,
                            height = height,
                        )
                    }
            }
    }

    override fun buildJVMArgs(args: List<JsonElement>, launcherName: String, launcherVersion: String): List<String> {

        val ignoredArgs = setOf(
            "-cp",
            "\${classpath}"
        )

        return args
            .filter { element ->
                when (element) {
                    is JsonPrimitive -> true
                    is JsonObject -> isArgAllowed(element)
                    else -> false
                }
            }
            .flatMap { element ->
                when (element) {
                    is JsonPrimitive -> {
                        listOf(element.content)
                    }

                    is JsonObject -> {
                        when (val value = element["value"]) {
                            is JsonPrimitive -> {
                                listOf(value.content)
                            }

                            is JsonArray -> {
                                value
                                    .filterIsInstance<JsonPrimitive>()
                                    .map { it.content }
                            }

                            else -> emptyList()
                        }
                    }

                    else -> emptyList()
                }
            }
            .filterNot { it in ignoredArgs }
            .map { argument ->
                resolveJVMArgument(
                    argument
                )
            }
    }

    override fun buildClasspath(libraries: List<Pair<String, File>>, versionId: String): String {

        val libraries = libraries.map { it.second }

        val clientJar = File(LauncherPaths.versions, "$versionId.jar").absolutePath

        return (libraries + clientJar)
            .joinToString(File.pathSeparator)
    }
}