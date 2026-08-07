package com.moddy.moddylauncher.data.remote

import com.moddy.moddylauncher.domain.adoptium.Adoptium
import com.moddy.moddylauncher.domain.adoptium.AssetItem

interface AdoptiumApi {

    suspend fun getAdoptiumReleases(): Adoptium?

    suspend fun getAssetsById(id: String, arch: String, os: String): AssetItem?
}