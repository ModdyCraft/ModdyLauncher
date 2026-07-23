package com.moddy.moddylauncher.domain.version

data class AssetIndex(
    val id: String,
    val sha1: String,
    val size: Int,
    val totalSize: Int,
    val url: String
)