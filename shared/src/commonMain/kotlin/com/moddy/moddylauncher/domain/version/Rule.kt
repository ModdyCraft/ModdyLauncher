package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class Rule(
    val action: String,
    val os: Os
)