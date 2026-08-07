package com.moddy.moddylauncher.data.remote

import com.moddy.moddylauncher.domain.adoptium.Adoptium

interface AdoptiumApi {

    suspend fun getAdoptiumReleases(): Adoptium?
}