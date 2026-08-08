package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class DownloadsX(
    val artifact: Artifact? = null
)