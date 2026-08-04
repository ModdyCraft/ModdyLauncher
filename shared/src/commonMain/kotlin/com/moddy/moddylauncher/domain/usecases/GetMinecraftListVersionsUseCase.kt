package com.moddy.moddylauncher.domain.usecases

import com.moddy.moddylauncher.LauncherPaths
import com.moddy.moddylauncher.data.remote.MinecraftApi
import com.moddy.moddylauncher.domain.model.MCVersion

class GetMinecraftListVersionsUseCase(
    private val api: MinecraftApi
) {
    suspend operator fun invoke(vararg filter: VersionType): List<MCVersion> {

        val versions = api.getVersions(*filter)
            .map {
                val installed = LauncherPaths.versions.resolve("${it.id}.json").exists()
                MCVersion(it.id, installed)
            }

        return versions
    }
}

enum class VersionType {
    snapshot,
    release,
    old_beta
}

enum class ClientType {
    vanilla,
    forge,
    neo_forge,
    optifine,
    fabric
}

enum class JavaExecutable