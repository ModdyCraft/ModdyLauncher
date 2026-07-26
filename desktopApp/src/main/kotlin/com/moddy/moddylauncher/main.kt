package com.moddy.moddylauncher

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.moddy.moddylauncher.di.InitKoin

fun main() = application {

    LauncherPaths.initBaseStructure()

    InitKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "ModdyLauncher",
    ) {
        App()
    }
}