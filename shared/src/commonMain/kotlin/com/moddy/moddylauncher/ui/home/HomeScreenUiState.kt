package com.moddy.moddylauncher.ui.home

import com.moddy.moddylauncher.domain.model.MCVersion

data class HomeScreenUiState(
    val nickName: String = "",

    val versions: List<MCVersion> = emptyList(),
    val versionSelected: MCVersion? = null,

    val userName: String = "",
)
