package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class Server(
    val sha1: String,
    val size: Int,
    val url: String
)