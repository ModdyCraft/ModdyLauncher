package com.moddy.moddylauncher.domain.usecases

import com.moddy.moddylauncher.data.local.MinecraftRepository

class LaunchMinecraftUseCase(
    private val launcher: MinecraftRepository,
) {
    suspend operator fun invoke(version: String) {
        launcher.playVersion(version)
    }
}