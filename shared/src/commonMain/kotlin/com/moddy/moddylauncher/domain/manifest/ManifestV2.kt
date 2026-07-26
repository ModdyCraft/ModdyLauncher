package com.moddy.moddylauncher.domain.manifest

import kotlinx.serialization.Serializable

@Serializable
data class ManifestV2(
    val latest: Latest,
    val versions: List<Version>
)