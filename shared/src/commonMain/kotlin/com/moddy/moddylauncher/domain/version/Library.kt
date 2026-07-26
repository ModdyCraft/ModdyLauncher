package com.moddy.moddylauncher.domain.version

import kotlinx.serialization.Serializable

@Serializable
data class Library(
    val downloads: DownloadsX,
    val name: String,
    val rules: List<RuleX>
)