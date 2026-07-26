package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.LauncherPaths
import com.moddy.moddylauncher.data.download.DownloadRepository
import com.moddy.moddylauncher.data.remote.MinecraftApi
import com.moddy.moddylauncher.domain.version.DefaultUserJvm
import com.moddy.moddylauncher.domain.version.Library
import com.moddy.moddylauncher.domain.version.VersionManifest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.*
import java.io.File

class MinecraftRepoImpl(
    private val api: MinecraftApi,
    private val downloader: DownloadRepository,
    private val client: HttpClient,
) : MinecraftRepository {

    private val json = Json { prettyPrint = true }

    override suspend fun playVersion(versionId: String) {
        val version = api.getVersion(versionId)

        // Descargando Manifest
        val manifest = json.encodeToString(version)
        File(LauncherPaths.versions, "${version.id}.json").writeText(manifest)

        // Descargando Cliente
        downloader.downloadFile(version.downloads.client.url, File(LauncherPaths.versions, "${version.id}.jar"))

        val libraries = version.libraries.mapNotNull { library ->
            if (!isLibraryAllowed(library)) {
                return@mapNotNull null
            } else {
                Pair(library.downloads.artifact.url, File(LauncherPaths.libraries, library.downloads.artifact.path))
            }
        }

        // Descargando Dependencias
        downloader.downloadFilesInParallel(libraries, 6)

        // Descargando Assets
        downloadAssets(version)

        execute(version)
    }

    private suspend fun downloadAssets(version: VersionManifest) {
        val manifestFile = LauncherPaths.index.resolve("${version.assetIndex.id}.json")

        val manifest = if (manifestFile.exists()) {
            json.parseToJsonElement(manifestFile.readText())
        } else {
            client.get(version.assetIndex.url)
                .body<JsonElement>()
                .also { manifest ->
                    manifestFile.writeText(json.encodeToString(manifest))
                }
        }

        val assets = manifest.jsonObject["objects"]!!.jsonObject.values.map { value ->

            val hash = value.jsonObject["hash"]?.jsonPrimitive?.content
                ?: error("Asset manifest entry does not contain a hash")

            val folder = hash.take(2)

            val url = "https://resources.download.minecraft.net/$folder/$hash"
            val destination = LauncherPaths.objects
                .resolve(folder)
                .resolve(hash)

            url to destination
        }

        downloader.downloadFilesInParallel(assets, 12)
    }

    private fun isLibraryAllowed(library: Library): Boolean {
        if (library.rules == null) return true

        val currentOs = when {
            LauncherPaths.os.contains("win") -> "windows"
            LauncherPaths.os.contains("mac") -> "osx"
            else -> "linux"
        }

        var allowed = false

        for ((action, os) in library.rules) {

            if (os.name == currentOs) {
                allowed = action == "allow"
            }
        }

        return allowed
    }

    private fun execute(version: VersionManifest) {
        val defaultUserJvm = version.arguments.defaultUserJvm
            .mapNotNull { arg ->
                if (isArgAllowed(arg)) {
                    arg.value
                } else {
                    null
                }
            }
            .flatten()

        val command = mutableListOf<String>()

        // Java
        command += javaExecutable

        // Argumentos JVM
        command += defaultUserJvm

        // Classpath
        command += "-cp"
        command += buildClasspath(version)

        // Main class
        command += version.mainClass

        // Argumentos del juego
        command += version.arguments.game
            .filter { element ->
                when (element) {
                    is JsonPrimitive -> {
                        true
                    }

                    is JsonObject -> {
                        isArgAllowed(element)
                    }

                    else -> {
                        false
                    }
                }
            }
            .flatMap { element ->
                when (element) {
                    is JsonPrimitive -> {
                        val argument = element.content

                        if (argument in setOf(
                                "--demo",
                                "--quickPlayPath",
                                "--quickPlaySingleplayer",
                                "--quickPlayMultiplayer",
                                "--quickPlayRealms"
                            )
                        ) {
                            emptyList()
                        } else {
                            listOf(
                                resolveArgument(
                                    argument,
                                    version
                                )
                            )
                        }
                    }

                    is JsonObject -> {
                        when (val value = element["value"]) {
                            is JsonPrimitive -> {
                                val argument = value.content

                                if (argument in setOf(
                                        "--demo",
                                        "--quickPlayPath",
                                        "--quickPlaySingleplayer",
                                        "--quickPlayMultiplayer",
                                        "--quickPlayRealms"
                                    )
                                ) {
                                    emptyList()
                                } else {
                                    listOf(
                                        resolveArgument(
                                            argument,
                                            version
                                        )
                                    )
                                }
                            }

                            is JsonArray -> {
                                value
                                    .filterIsInstance<JsonPrimitive>()
                                    .map { it.content }
                                    .filterNot {
                                        it in setOf(
                                            "--demo",
                                            "--quickPlayPath",
                                            "--quickPlaySingleplayer",
                                            "--quickPlayMultiplayer",
                                            "--quickPlayRealms"
                                        )
                                    }
                                    .map {
                                        resolveArgument(
                                            it,
                                            version
                                        )
                                    }
                            }

                            else -> emptyList()
                        }
                    }

                    else -> emptyList()
                }
            }

        println("Executing Minecraft:")
        println(command.joinToString(" "))

        val process = ProcessBuilder(command)
            .directory(LauncherPaths.launcher)
            .inheritIO()
            .start()

        val exitCode = process.waitFor()

        println("Minecraft exited with code: $exitCode")
    }

    private fun isArgAllowed(element: JsonObject): Boolean {
        val rules = element["rules"]?.jsonArray ?: return true

        var allowed = false

        for (rule in rules) {
            val ruleObject = rule.jsonObject
            val action = ruleObject["action"]?.jsonPrimitive?.content
                ?: continue

            val os = ruleObject["os"]?.jsonObject

            if (os == null) {
                allowed = action == "allow"
                continue
            }

            val currentOs = when {
                LauncherPaths.os.contains("win", ignoreCase = true) -> "windows"
                LauncherPaths.os.contains("mac", ignoreCase = true) -> "osx"
                else -> "linux"
            }

            val ruleOs = os["name"]?.jsonPrimitive?.content

            if (ruleOs == currentOs) {
                allowed = action == "allow"
            }
        }

        return allowed
    }

    private fun isArgAllowed(arg: DefaultUserJvm): Boolean {
        if (arg.rules == null) return true

        val currentOs = when {
            LauncherPaths.os.contains("win", ignoreCase = true) -> "windows"
            LauncherPaths.os.contains("mac", ignoreCase = true) -> "osx"
            else -> "linux"
        }

        var allowed = false

        for ((action, os) in arg.rules) {
            if (os?.name != currentOs) continue

            // Si es Windows, comprobar el rango de versión
            if (currentOs == "windows" && os.versionRange != null) {
                val currentVersion = LauncherPaths.osVersion

                val minVersion = os.versionRange.min
                val maxVersion = os.versionRange.max

                if (minVersion != null && compareVersions(currentVersion, minVersion) < 0) {
                    continue
                }

                if (maxVersion != null && compareVersions(currentVersion, maxVersion) > 0) {
                    continue
                }
            }

            allowed = action == "allow"
        }

        return allowed
    }

    private fun compareVersions(
        current: String,
        target: String
    ): Int {
        val currentParts = current.split(".").map { it.toIntOrNull() ?: 0 }
        val targetParts = target.split(".").map { it.toIntOrNull() ?: 0 }

        val maxSize = maxOf(currentParts.size, targetParts.size)

        for (i in 0 until maxSize) {
            val currentPart = currentParts.getOrElse(i) { 0 }
            val targetPart = targetParts.getOrElse(i) { 0 }

            if (currentPart < targetPart) return -1
            if (currentPart > targetPart) return 1
        }

        return 0
    }

    private fun buildClasspath(version: VersionManifest): String {
        val libraries = version.libraries
            .filter { isLibraryAllowed(it) }
            .map { library ->
                File(LauncherPaths.libraries, library.downloads.artifact.path)
            }

        val clientJar = File(LauncherPaths.versions, "${version.id}.jar").absolutePath

        return (libraries + clientJar)
            .joinToString(File.pathSeparator)
    }

    private fun resolveArgument(
        argument: String,
        version: VersionManifest
    ): String {

        val variables = mapOf(
            "\${auth_player_name}" to "Moddy",
            "\${version_name}" to version.id,
            "\${game_directory}" to LauncherPaths.versions.absolutePath,
            "\${assets_root}" to LauncherPaths.assets.absolutePath,
            "\${assets_index_name}" to version.assetIndex.id.orEmpty(),
            "\${auth_uuid}" to "TU-UUID",
            "\${auth_access_token}" to "TU-ACCESS-TOKEN",
            "\${clientid}" to "TU-CLIENT-ID",
            "\${auth_xuid}" to "TU-XUID",
            "\${version_type}" to "release",
            "\${resolution_width}" to "548",
            "\${resolution_height}" to "408",
            "\${quickPlayPath}" to "TU-QUICK-PLAY-PATH",
            "\${quickPlaySingleplayer}" to "TU-QUICK-PLAY-SINGLEPLAYER",
            "\${quickPlayMultiplayer}" to "TU-QUICK-PLAY-MULTIPLAYER",
            "\${quickPlayRealms}" to "TU-QUICK-PLAY-REALMS"
        )

        return variables[argument] ?: argument
    }
}

val javaExecutable = ProcessHandle.current()
    .info()
    .command()
    .orElse(null)