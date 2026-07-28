package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class Os(
    val name: String,
    val versionRange: VersionRange? = null,
    val arch: String? = null,
)