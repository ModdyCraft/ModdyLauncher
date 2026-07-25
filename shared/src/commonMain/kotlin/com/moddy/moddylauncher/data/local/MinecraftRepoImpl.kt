package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.LauncherPaths
import com.moddy.moddylauncher.data.download.DownloadRepository
import com.moddy.moddylauncher.data.remote.MinecraftApi
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
) : MinecraftRepository {

    private val json = Json { prettyPrint = true }

    override suspend fun playVersion(versionId: String) {
        val version = api.getVersion(versionId)

        // Descargando Manifest
        val manifest = json.encodeToString(version)
        File(LauncherPaths.versions, "${version.id}.json").writeText(manifest)

        // Descargando Cliente
        downloader.downloadFile(version.downloads.client.url, File(LauncherPaths.versions, "${version.id}.jar"))

        val libraries = version.libraries.map { library ->
            Pair(library.downloads.artifact.url, File(LauncherPaths.libraries, library.downloads.artifact.path))
        }

        // Descargando Dependencias
        downloader.downloadFilesInParallel(libraries, 6)

        // Descargando Assets
        downloadAssets(version)
    }

    private suspend fun downloadAssets(version: VersionManifest) {
        val manifestFile = File(LauncherPaths.index, "${version.assetIndex.id}.json")

        var manifest: JsonElement? = null

        if (!manifestFile.exists()) {
            manifest = client.get(version.assetIndex.url).body<JsonElement>()
            manifestFile.writeText(json.encodeToString(manifest))
        }

        manifest = json.parseToJsonElement(manifestFile.readText())

        val assets = manifest.jsonObject.entries.map { (name, value) ->
            val obj = value.jsonObject

            val hash = obj["hash"]!!.jsonPrimitive.content

            val folder = hash.substring(0, 2)

            val url = "https://resources.download.minecraft.net/$folder/$hash"

            val destination = LauncherPaths.objects.resolve(folder).resolve(hash)

            return@map Pair(url, destination)
        }

        downloader.downloadFilesInParallel(assets, 6)
    }
}