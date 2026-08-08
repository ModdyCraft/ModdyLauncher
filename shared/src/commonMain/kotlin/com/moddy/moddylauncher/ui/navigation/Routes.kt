package com.moddy.moddylauncher.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object Splash

@Serializable
object Auth

@Serializable
data class InstanceManager(
    val id: Int? = null,
)