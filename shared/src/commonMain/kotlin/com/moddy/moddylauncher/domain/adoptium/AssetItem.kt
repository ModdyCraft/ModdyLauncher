package com.moddy.moddylauncher.domain.adoptium


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetItem(
    @SerialName("binary")
    val binary: Binary,
    @SerialName("release_link")
    val releaseLink: String,
    @SerialName("release_name")
    val releaseName: String,
    @SerialName("vendor")
    val vendor: String,
    @SerialName("version")
    val version: Version
)