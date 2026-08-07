package com.moddy.moddylauncher.domain.adoptium

import kotlinx.serialization.Serializable

@Serializable
data class Adoptium(
    val available_lts_releases: List<Int>,
    val available_releases: List<Int>,
    val most_recent_feature_release: Int,
    val most_recent_feature_version: Int,
    val most_recent_lts: Int,
    val tip_version: Int
)