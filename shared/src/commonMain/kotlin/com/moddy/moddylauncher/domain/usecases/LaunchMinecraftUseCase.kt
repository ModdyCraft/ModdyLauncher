package com.moddy.moddylauncher.domain.usecases

import com.moddy.moddylauncher.data.local.MinecraftRepository
import com.moddy.moddylauncher.database.instance.InstanceData

class LaunchMinecraftUseCase(
    private val launcher: MinecraftRepository,
) {
    suspend operator fun invoke(instance: InstanceData, output: (String) -> Unit) {
        launcher.playVersion(instance, output = output)
    }
}