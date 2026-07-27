package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.common.MemoryRam
import com.moddy.moddylauncher.domain.version.DefaultUserJvm
import com.moddy.moddylauncher.domain.version.Library
import kotlinx.serialization.json.JsonElement

interface MinecraftLauncher {

    fun launch()

    fun buildDefaultJvmArgs(
        args: List<DefaultUserJvm>,
        minMem: MemoryRam = MemoryRam.G2,
        maxMem: MemoryRam = MemoryRam.G4
    ): List<String>

    fun buildGameArgs(args: List<JsonElement>, assetIndex: String, versionId: String): List<String>

    fun buildJVMArgs(args: List<JsonElement>): List<String>

    fun buildClasspath(libraries: List<Library>, versionId: String): String
}