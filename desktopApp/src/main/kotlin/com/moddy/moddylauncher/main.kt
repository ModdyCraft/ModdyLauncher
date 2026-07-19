package com.moddy.moddylauncher

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {

    LauncherPaths.initBaseStructure()

    Window(
        onCloseRequest = ::exitApplication,
        title = "ModdyLauncher",
    ) {
        App()
    }
}