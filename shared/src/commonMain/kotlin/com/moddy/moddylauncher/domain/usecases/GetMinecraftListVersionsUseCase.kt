package com.moddy.moddylauncher.domain.usecases

import com.moddy.moddylauncher.data.remote.MinecraftApi

class GetMinecraftListVersionsUseCase(
    private val api: MinecraftApi
) {
    suspend operator fun invoke(): List<String> {
        return api.getVersions().map { it.id }
    }
}