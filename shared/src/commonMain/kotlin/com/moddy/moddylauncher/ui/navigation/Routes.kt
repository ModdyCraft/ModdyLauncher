package com.moddy.moddylauncher.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data class Home(
    val reload: Boolean? = null,
)

@Serializable
class Splash

@Serializable
class Auth

@Serializable
data class InstanceManager(
    val id: Int? = null
)