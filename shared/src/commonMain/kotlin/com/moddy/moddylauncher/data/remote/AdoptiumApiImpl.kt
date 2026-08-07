package com.moddy.moddylauncher.data.remote

import com.moddy.moddylauncher.domain.adoptium.Adoptium
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class AdoptiumApiImpl(
    private val httpClient: HttpClient,
) : AdoptiumApi {

    private var adoptium: Adoptium? = null

    override suspend fun getAdoptiumReleases(): Adoptium? {

        if (adoptium == null) {
            adoptium = httpClient.get("https://adoptium.com").body()
        }

        return adoptium
    }
}