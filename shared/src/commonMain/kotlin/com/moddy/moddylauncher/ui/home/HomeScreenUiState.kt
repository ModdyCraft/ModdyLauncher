package com.moddy.moddylauncher.ui.home

data class HomeScreenUiState(
    val nickName: String = "",

    val versions: List<String> = emptyList(),
    val versionSelected: String = "",

    val userName: String = "",
)
