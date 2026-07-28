package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class RuleX(
    val action: String,
    val os: OsX
)