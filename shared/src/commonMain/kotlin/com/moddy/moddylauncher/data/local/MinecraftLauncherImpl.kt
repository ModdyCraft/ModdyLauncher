package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.common.MemoryRam
import com.moddy.moddylauncher.common.isArgAllowed
import com.moddy.moddylauncher.common.resolveArgument
import com.moddy.moddylauncher.domain.version.DefaultUserJvm
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class MinecraftLauncherImpl : MinecraftLauncher {
    override fun launch() {
        TODO("Not yet implemented")
    }

    override fun buildDefaultJvmArgs(args: List<DefaultUserJvm>, minMem: MemoryRam, maxMem: MemoryRam): List<String> {

        val newList = args.mapNotNull {
            if (isArgAllowed(it)) it.value else null
        }.flatten().toMutableList()

        newList.remove("-Xms2G")
        newList.remove("-Xmx4G")

        newList.addFirst("-Xmx${maxMem.name.reversed()}")
        newList.addFirst("-Xms${minMem.name.reversed()}")

        return newList
    }

    override fun buildGameArgs(args: List<JsonElement>): List<String> {
        val ignoredArgs = setOf(
            "--demo",
            "--quickPlayPath",
            "--quickPlaySingleplayer",
            "--quickPlayMultiplayer",
            "--quickPlayRealms",
            "\${quickPlayPath}",
            "\${quickPlaySingleplayer}",
            "\${quickPlayMultiplayer}",
            "\${quickPlayRealms}"
        )

        return args
            .filter { element ->
                element is JsonPrimitive ||
                        element is JsonObject && isArgAllowed(element)
            }
            .flatMap { element ->
                val values = when (element) {
                    is JsonPrimitive -> listOf(element.content)

                    is JsonObject -> when (val value = element["value"]) {
                        is JsonPrimitive -> listOf(value.content)
                        is JsonArray -> value
                            .filterIsInstance<JsonPrimitive>()
                            .map { it.content }

                        else -> emptyList()
                    }

                    else -> emptyList()
                }

                values
                    .filterNot { it in ignoredArgs }
                    .map {
                        resolveArgument(
                            it,
                            versionId = "TODO()",
                            assetIndex = "TODO()"
                        )
                    }
            }
    }

    override fun buildJVMArgs(args: List<JsonElement>): List<String> {
        TODO("Not yet implemented")
    }

    override fun buildClasspath() {
        TODO("Not yet implemented")
    }
}