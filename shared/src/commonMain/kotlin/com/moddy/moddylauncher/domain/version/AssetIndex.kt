package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class AssetIndex(
    val id: String,
    val sha1: String,
    val size: Int,
    val totalSize: Int,
    val url: String
)