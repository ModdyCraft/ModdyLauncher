package com.moddy.moddylauncher.domain.adoptium


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Version(
    @SerialName("build")
    val build: Int,
    @SerialName("major")
    val major: Int,
    @SerialName("minor")
    val minor: Int,
    @SerialName("openjdk_version")
    val openjdkVersion: String,
    @SerialName("security")
    val security: Int,
    @SerialName("semver")
    val semver: String
)