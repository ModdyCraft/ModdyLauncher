package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class Downloads(
    val client: Client,
    val server: Server
)