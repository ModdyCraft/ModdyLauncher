package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.LauncherPaths
import com.moddy.moddylauncher.data.download.DownloadRepository
import com.moddy.moddylauncher.data.remote.MinecraftApi
import com.moddy.moddylauncher.database.instance.InstanceData
import com.moddy.moddylauncher.domain.version.Library
import com.moddy.moddylauncher.domain.version.VersionManifest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File

class MinecraftRepoImpl(
    private val api: MinecraftApi,
    private val downloader: DownloadRepository,
    private val client: HttpClient,
    private val launcher: MinecraftLauncher,
    private val jreDownloader: AdoptiumRepoImpl
) : MinecraftRepository {

    private val json = Json { prettyPrint = true }

    override suspend fun playVersion(instance: InstanceData, output: (String) -> Unit): Process {

        output("[FETCHING-MANIFEST]: Fetching Minecraft version")

        val version = api.getVersion(instance.version)

        output("[FETCHING-MANIFEST]: Fetched version: ${version.id}")

        // Descargando Cliente
        output("[DOWNLOADING]: Downloading Minecraft Client")
        downloader.downloadFile(
            version.downloads.client.url,
            File(LauncherPaths.versions, "${version.id}.jar"),
            output = output
        )

        // Filtrado de librerias
        output("[LIBRARY]: Filtering Libraries")
        val libraries = version.libraries.mapNotNull { library ->
            if (!isLibraryAllowed(library)) {
                return@mapNotNull null
            } else {
                library.downloads.artifact?.let {
                    Pair(
                        it.url, File(LauncherPaths.libraries, library.downloads.artifact.path)
                    )
                }
            }
        }

        output("[LIBRARY]: Filtering finished: ${libraries.size}")

        // Descargando JRE
        output("[JAVA-RUNTIME-EPILSON]: Downloading JRE")
        val jre = jreDownloader.downloadAdoptium(
            if (instance.javaExec.contains("Default")) version.javaVersion.majorVersion.toString() else instance.javaExec,
            output = output
        )

        // Descargando Dependencias
        output("[LIBRARY]: Downloading libraries in parallel")
        downloader.downloadFilesInParallel(libraries, 6, output)

        // Descargando Assets
        output("[ASSETS]: DOWNLOADING ASSETS")
        downloadAssets(version, output)

        // Descargando Manifest
        output("[MANIFEST]: Downloading manifest")
        val manifest = json.encodeToString(version)
        File(LauncherPaths.versions, "${version.id}.json").writeText(manifest)
        output("[MANIFEST]: Downloaded manifest")

        return execute(
            version, instance = instance, jre = jre, libraries = libraries, output = output
        )
    }

    private suspend fun downloadAssets(version: VersionManifest, output: (String) -> Unit) {
        val manifestFile = LauncherPaths.index.resolve("${version.assetIndex.id}.json")

        val manifest = if (manifestFile.exists()) {
            json.parseToJsonElement(manifestFile.readText())
        } else {
            client.get(version.assetIndex.url).body<JsonElement>().also { manifest ->
                manifestFile.writeText(json.encodeToString(manifest))
            }
        }

        val assets = manifest.jsonObject["objects"]!!.jsonObject.values.map { value ->

            val hash = value.jsonObject["hash"]?.jsonPrimitive?.content
                ?: error("Asset manifest entry does not contain a hash")

            val folder = hash.take(2)

            val url = "https://resources.download.minecraft.net/$folder/$hash"
            val destination = LauncherPaths.objects.resolve(folder).resolve(hash)

            url to destination
        }

        downloader.downloadFilesInParallel(assets, 12, output)
    }

    private fun isLibraryAllowed(library: Library): Boolean {
        val rules = library.rules ?: return true

        val currentOs = when {
            LauncherPaths.os.contains("win", ignoreCase = true) -> "windows"
            LauncherPaths.os.contains("mac", ignoreCase = true) -> "osx"
            else -> "linux"
        }

        var allowed = false

        for ((action, os) in rules) {

            // Regla global: aplica independientemente del sistema operativo
            if (os == null) {
                allowed = action == "allow"
                continue
            }

            // Regla específica para un sistema operativo
            if (os.name == currentOs) {
                allowed = action == "allow"
            }
        }

        return allowed
    }

    private suspend fun execute(
        version: VersionManifest,
        instance: InstanceData,
        jre: File,
        libraries: List<Pair<String, File>>,
        output: (String) -> Unit
    ): Process {
        return launcher.launch(version, instance, jre = jre, libraries = libraries, output = output)
    }
}