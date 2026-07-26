package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.common.MemoryRam
import com.moddy.moddylauncher.domain.version.DefaultUserJvm
import kotlinx.serialization.json.JsonElement

interface MinecraftLauncher {

    fun launch()

    fun buildDefaultJvmArgs(
        args: List<DefaultUserJvm>,
        minMem: MemoryRam = MemoryRam.G2,
        maxMem: MemoryRam = MemoryRam.G4
    ): List<String>

    fun buildGameArgs(args: List<JsonElement>): List<String>

    fun buildJVMArgs(args: List<JsonElement>): List<String>

    fun buildClasspath()
}