package com.moddy.moddylauncher.ui.instance

import com.moddy.moddylauncher.domain.usecases.ClientType
import com.moddy.moddylauncher.domain.usecases.VersionType

data class InstanceManagerUiState(
    val instanceName: String = "",
    val instanceNamePlaceHolder: String = "Default",
    val instancePath: String = "Default",
    val versionFilter: VersionType = VersionType.release,
    val clientFilter: ClientType = ClientType.vanilla,
    val version: String = "Default",
    val versions: List<String> = emptyList(),
    val javaExecutable: String = "Default",
    val JVMArgs: String = "",
    val height: String = "854",
    val width: String = "480",
    val fullWindow: Boolean = true,
)
