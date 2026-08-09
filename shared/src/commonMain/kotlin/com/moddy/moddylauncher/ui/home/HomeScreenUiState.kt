package com.moddy.moddylauncher.ui.home

import com.moddy.moddylauncher.database.instance.InstanceData

data class HomeScreenUiState(
    val userName: String = "",

    val instances: List<InstanceData>? = null,

    val loading: Boolean = false,
)
