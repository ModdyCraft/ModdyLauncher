package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class DefaultUserJvm(
    val rules: List<Rule>? = null,
    val value: List<String>? = null,
)