package com.moddy.moddylauncher.domain.manifest

import kotlinx.serialization.Serializable

@Serializable
data class Latest(
    val release: String,
    val snapshot: String
)