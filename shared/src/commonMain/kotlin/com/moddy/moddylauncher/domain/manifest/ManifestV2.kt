package com.moddy.moddylauncher.domain.manifest

data class ManifestV2(
    val latest: Latest,
    val versions: List<Version>
)