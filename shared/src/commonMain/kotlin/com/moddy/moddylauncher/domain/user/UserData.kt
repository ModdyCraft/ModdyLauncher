package com.moddy.moddylauncher.domain.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserData(
    @SerialName("username")
    val username: String,
    @SerialName("uuid")
    val uuid: String = UUID.randomUUID().toString(),
)
