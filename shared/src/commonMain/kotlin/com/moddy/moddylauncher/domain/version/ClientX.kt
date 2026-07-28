package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class ClientX(
    val argument: String,
    val `file`: File,
    val type: String
)