package com.moddy.moddylauncher.domain.usecases

import com.moddy.moddylauncher.data.remote.MinecraftApi

class DownloadManifestUseCase(
    private val repository: MinecraftApi
) {
    suspend operator fun invoke() {
        repository.getManifest()
    }
}