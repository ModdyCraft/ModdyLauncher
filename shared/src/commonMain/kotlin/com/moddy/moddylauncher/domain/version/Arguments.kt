package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement

data class Arguments(
    @SerialName("default-user-jvm")
    val defaultUserJvm: List<DefaultUserJvm>,
    val game: List<JsonElement>,
    val jvm: List<JsonElement>
)