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

    override suspend fun playVersion(instance: InstanceData) {
        val version = api.getVersion(instance.version)

        // Descargando Cliente
        downloader.downloadFile(version.downloads.client.url, File(LauncherPaths.versions, "${version.id}.jar"))

        val libraries = version.libraries.mapNotNull { library ->
            if (!isLibraryAllowed(library)) {
                return@mapNotNull null
            } else {
                library.downloads.artifact?.let {
                    Pair(
                        it.url,
                        File(LauncherPaths.libraries, library.downloads.artifact.path)
                    )
                }
            }
        }

        // Descargando JRE
        val jre = jreDownloader.downloadAdoptium(
            if (instance.javaExec.contains("Default")) version.javaVersion.majorVersion.toString() else instance.javaExec
        )

        // Descargando Dependencias
        downloader.downloadFilesInParallel(libraries, 6)

        // Descargando Assets
        downloadAssets(version)

        // Descargando Manifest
        val manifest = json.encodeToString(version)
        File(LauncherPaths.versions, "${version.id}.json").writeText(manifest)

        execute(
            version, instance = instance, jre = jre
        )
    }

    private suspend fun downloadAssets(version: VersionManifest) {
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

            if (os?.name == currentOs) {
                allowed = action == "allow"
            }
        }

        return allowed
    }

    private suspend fun execute(version: VersionManifest, instance: InstanceData, jre: File) {
        launcher.launch(version, instance, jre = jre)
    }
}