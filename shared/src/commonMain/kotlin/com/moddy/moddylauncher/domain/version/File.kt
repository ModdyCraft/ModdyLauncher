package com.moddy.moddylauncher.domain.version

data class File(
    val id: String,
    val sha1: String,
    val size: Int,
    val url: String
)