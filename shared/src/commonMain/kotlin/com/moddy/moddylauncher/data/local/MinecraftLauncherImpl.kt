package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.LauncherPaths
import com.moddy.moddylauncher.common.MemoryRam
import com.moddy.moddylauncher.common.isArgAllowed
import com.moddy.moddylauncher.common.resolveArgument
import com.moddy.moddylauncher.common.resolveJVMArgument
import com.moddy.moddylauncher.database.user.UserDTO
import com.moddy.moddylauncher.database.user.UserData
import com.moddy.moddylauncher.domain.version.DefaultUserJvm
import com.moddy.moddylauncher.domain.version.Library
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
    override suspend fun launch(version: VersionManifest) {

        val defaultUserJvm = buildDefaultJvmArgs(
            args = version.arguments.defaultUserJvm,
            minMem = MemoryRam.G2,
            maxMem = MemoryRam.G4
        )

        val gameArgs = buildGameArgs(
            args = version.arguments.game,
            assetIndex = version.assetIndex.id,
            versionId = version.id,
            versionType = version.type,
        )

        val jvmArgs = buildJVMArgs(
            args = version.arguments.jvm,
            launcherName = "ModdyLauncher",
            launcherVersion = "1.0.0"
        )

        val classPath = buildClasspath(
            libraries = version.libraries,
            versionId = version.id
        )

        val command = mutableListOf<String>()

        // Cambiar por un ejecutable real en tu Dispositivo
        command.add("C:\\Users\\ModdyDev\\.jdks\\openjdk-26.0.1\\bin\\javaw.exe")

        command.addAll(defaultUserJvm)
        command.addAll(jvmArgs)

        command.add("-cp")
        command.add(classPath)

        command.add(version.mainClass)

        command.addAll(gameArgs)

        println(command)

        withContext(Dispatchers.IO) {
            ProcessBuilder(command)
                .directory(LauncherPaths.newProfile(version.id).root)
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
        versionType: String
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
                            assetIndex = assetIndex,
                            userData = user ?: UserData("NO NAMED"),
                            versionType = versionType
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

    override fun buildClasspath(libraries: List<Library>, versionId: String): String {
        val libraries = libraries
            .map { library ->
                File(LauncherPaths.libraries, library.downloads.artifact.path)
            }

        val clientJar = File(LauncherPaths.versions, "$versionId.jar").absolutePath

        return (libraries + clientJar)
            .joinToString(File.pathSeparator)
    }
}