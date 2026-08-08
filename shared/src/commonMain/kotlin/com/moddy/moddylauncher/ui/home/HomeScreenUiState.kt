package com.moddy.moddylauncher.ui.home

import com.moddy.moddylauncher.database.instance.InstanceData
import com.moddy.moddylauncher.domain.model.MCVersion

data class HomeScreenUiState(
    val versions: List<MCVersion> = emptyList(),
    val versionSelected: MCVersion? = null,

    val userName: String = "",

    val instances: List<InstanceData>? = null,

    val loading: Boolean = false,
)
