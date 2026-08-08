package com.moddy.moddylauncher.data.remote

import com.moddy.moddylauncher.domain.adoptium.Adoptium
import com.moddy.moddylauncher.domain.adoptium.AssetItem
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class AdoptiumApiImpl(
    private val httpClient: HttpClient,
) : AdoptiumApi {

    private var adoptium: Adoptium? = null

    override suspend fun getAdoptiumReleases(): Adoptium? {

        if (adoptium == null) {
            adoptium = httpClient.get("https://api.adoptium.net/v3/info/available_releases").body()
        }

        return adoptium
    }

    override suspend fun getAssetsById(id: String, arch: String, os: String): AssetItem {
        return httpClient
            .get("https://api.adoptium.net/v3/assets/latest/$id/hotspot") {
                parameter("architecture", arch)
                parameter("image_type", "jre")
                parameter("os", os)
                parameter("vendor", "eclipse")
            }
            .body<List<AssetItem>>()
            .first()
    }
}