package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class VersionRange(
    val max: String? = null,
    val min: String? = null,
)