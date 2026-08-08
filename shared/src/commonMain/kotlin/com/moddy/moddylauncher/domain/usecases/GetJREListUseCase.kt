package com.moddy.moddylauncher.domain.usecases

import com.moddy.moddylauncher.data.remote.AdoptiumApi

class GetJREListUseCase(
    private val adoptiumApi: AdoptiumApi
) {
    suspend operator fun invoke(): List<Int>? {
        return adoptiumApi.getAdoptiumReleases()?.available_releases
    }
}