package com.moddy.moddylauncher

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.moddy.moddylauncher.di.InitKoin

fun main() = application {

    LauncherPaths.initBaseStructure()

    InitKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "ModdyLauncher",
        state = rememberWindowState(
            width = 1000.dp,
            height = 650.dp
        ),
        icon = painterResource("icon.png"),
    ) {
        App()
    }
}