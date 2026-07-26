package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Arguments(
    @SerialName("default-user-jvm")
    val defaultUserJvm: List<DefaultUserJvm>,
    val game: List<DefaultUserJvm>,
    val jvm: List<DefaultUserJvm>
)