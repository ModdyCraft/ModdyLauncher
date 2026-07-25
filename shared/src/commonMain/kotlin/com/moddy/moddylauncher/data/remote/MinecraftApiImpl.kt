package com.moddy.moddylauncher.data.remote

import com.moddy.moddylauncher.LauncherPaths
import com.moddy.moddylauncher.domain.manifest.Latest
import com.moddy.moddylauncher.domain.manifest.ManifestV2
import com.moddy.moddylauncher.domain.manifest.Version
import com.moddy.moddylauncher.domain.version.VersionManifest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import java.io.File

class MinecraftApiImpl(
    val client: HttpClient,
) : MinecraftApi {
    override suspend fun getManifest(): ManifestV2 {
        return client.get("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json").body()
    }

    override suspend fun getLatest(): Latest {
        return getManifest().latest
    }

    override suspend fun getVersions(): List<Version> {
        return getManifest().versions
    }

    override suspend fun getVersion(versionId: String): VersionManifest {
        val manifest = File(LauncherPaths.versions, "$versionId.json")

        if (manifest.exists()) return Json.decodeFromString<VersionManifest>(manifest.readText())

        val ver = getManifest().versions.find { it.id == versionId }!!

        return client.get(ver.url).body()
    }
}