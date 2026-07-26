package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class DefaultUserJvm(
    val rules: List<Rule>,
    val value: List<String>
)