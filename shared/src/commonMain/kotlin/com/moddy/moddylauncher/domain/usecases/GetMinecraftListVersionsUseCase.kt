package com.moddy.moddylauncher.domain.usecases

import com.moddy.moddylauncher.data.remote.MinecraftApi

class GetMinecraftListVersionsUseCase(
    private val api: MinecraftApi
) {
    suspend operator fun invoke(vararg filter: VersionType) = api.getVersions(*filter).map { it.id }
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