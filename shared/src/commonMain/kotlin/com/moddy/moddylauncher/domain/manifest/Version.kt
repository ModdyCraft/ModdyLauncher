package com.moddy.moddylauncher.domain.manifest

import kotlinx.serialization.Serializable

@Serializable
data class Version(
    val complianceLevel: Int,
    val id: String,
    val releaseTime: String,
    val sha1: String,
    val time: String,
    val type: String,
    val url: String
)