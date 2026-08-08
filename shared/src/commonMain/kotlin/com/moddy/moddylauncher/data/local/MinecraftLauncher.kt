package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.common.MemoryRam
import com.moddy.moddylauncher.database.instance.InstanceData
import com.moddy.moddylauncher.domain.version.DefaultUserJvm
import com.moddy.moddylauncher.domain.version.VersionManifest
import kotlinx.serialization.json.JsonElement
import java.io.File

interface MinecraftLauncher {

    suspend fun launch(version: VersionManifest, instance: InstanceData, jre: File, libraries: List<Pair<String, File>>)

    fun buildDefaultJvmArgs(
        args: List<DefaultUserJvm>,
        minMem: MemoryRam = MemoryRam.G2,
        maxMem: MemoryRam = MemoryRam.G4
    ): List<String>

    fun buildGameArgs(
        args: List<JsonElement>,
        assetIndex: String,
        versionId: String,
        directory: String,
        versionType: String,
        width: String,
        height: String
    ): List<String>

    fun buildJVMArgs(args: List<JsonElement>, launcherName: String, launcherVersion: String): List<String>

    fun buildClasspath(libraries: List<Pair<String, File>>, versionId: String): String
}