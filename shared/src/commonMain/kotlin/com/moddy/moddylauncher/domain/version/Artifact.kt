package com.moddy.moddylauncher.domain.version

data class Artifact(
    val path: String,
    val sha1: String,
    val size: Int,
    val url: String
)