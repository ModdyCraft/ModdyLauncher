package com.moddy.moddylauncher

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform