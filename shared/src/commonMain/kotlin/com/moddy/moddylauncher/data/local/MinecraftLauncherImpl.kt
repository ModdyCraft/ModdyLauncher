package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.common.MemoryRam
import com.moddy.moddylauncher.common.isArgAllowed
import com.moddy.moddylauncher.domain.version.DefaultUserJvm
import kotlinx.serialization.json.JsonElement

class MinecraftLauncherImpl : MinecraftLauncher {
    override fun launch() {
        TODO("Not yet implemented")
    }

    override fun buildDefaultJvmArgs(args: List<DefaultUserJvm>, minMem: MemoryRam, maxMem: MemoryRam): List<String> {
        return args.mapNotNull {
            if (isArgAllowed(it)) it.value else null
        }.flatten()
    }

    override fun buildGameArgs(args: List<JsonElement>): List<String> {
        TODO("Not yet implemented")
    }

    override fun buildJVMArgs(args: List<JsonElement>): List<String> {
        TODO("Not yet implemented")
    }

    override fun buildClasspath() {
        TODO("Not yet implemented")
    }
}