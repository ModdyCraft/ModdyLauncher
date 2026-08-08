package com.moddy.moddylauncher.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
class Home

@Serializable
class Splash

@Serializable
class Auth

@Serializable
data class InstanceManager(
    val id: Int? = null
)