package com.moddy.moddylauncher.ui.instance

import com.moddy.moddylauncher.domain.usecases.ClientType
import com.moddy.moddylauncher.domain.usecases.VersionType

data class InstanceManagerUiState(
    val instanceName: String = "",
    val instancePath: String = "",
    val versionFilter: VersionType = VersionType.release,
    val clientFilter: ClientType = ClientType.vanilla,
    val version: String,
    val javaExecutable: String = "AUTOMATIC",
    val JVMArgs: String = "",
    val height: String = "",
    val width: String = "",
    val fullWindow: Boolean = false,
)
