package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.SerialName

data class Arguments(
    @SerialName("default-user-jvm")
    val defaultUserJvm: List<DefaultUserJvm>,
    val game: List<Any>,
    val jvm: List<Any>
)