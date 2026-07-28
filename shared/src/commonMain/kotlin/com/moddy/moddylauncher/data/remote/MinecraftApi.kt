package com.moddy.moddylauncher.data.remote

import com.moddy.moddylauncher.domain.manifest.Latest
import com.moddy.moddylauncher.domain.manifest.ManifestV2
import com.moddy.moddylauncher.domain.manifest.Version
import com.moddy.moddylauncher.domain.usecases.VersionType
import com.moddy.moddylauncher.domain.version.VersionManifest

interface MinecraftApi {

    suspend fun getManifest(): ManifestV2

    suspend fun getLatest(): Latest

    suspend fun getVersions(vararg filter: VersionType): List<Version>

    suspend fun getVersion(versionId: String): VersionManifest
}