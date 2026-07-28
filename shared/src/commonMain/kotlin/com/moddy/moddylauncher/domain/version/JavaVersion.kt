package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class JavaVersion(
    val component: String,
    val majorVersion: Int
)