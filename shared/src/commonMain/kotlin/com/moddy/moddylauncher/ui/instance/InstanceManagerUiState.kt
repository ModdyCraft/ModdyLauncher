package com.moddy.moddylauncher.ui.instance

import com.moddy.moddylauncher.domain.usecases.ClientType
import com.moddy.moddylauncher.domain.usecases.VersionType

data class InstanceManagerUiState(
    val instanceName: String = "",
    val instanceNamePlaceHolder: String = "Default",
    val instancePath: String = "Default",
    val versionFilters: List<VersionType> = listOf(VersionType.release, VersionType.old_beta, VersionType.snapshot),
    val versionFilter: VersionType = VersionType.release,
    val clientFilter: ClientType = ClientType.vanilla,
    val version: String = "Default",
    val versions: List<String> = emptyList(),
    val javaExecutable: String = "Default",
    val jreList: List<String> = listOf("Default"),
    val JVMArgs: String = "-Xms2G -Xmx4G -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -XX:+UseZGC",
    val height: String = "",
    val width: String = "",
    val fullWindow: Boolean = false,
    val loading: Boolean = false,
)
