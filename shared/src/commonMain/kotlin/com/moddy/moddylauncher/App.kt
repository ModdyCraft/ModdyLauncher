package com.moddy.moddylauncher

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.moddy.moddylauncher.ui.ModdyLauncherTheme
import com.moddy.moddylauncher.ui.navigation.NavScreen

@Composable
@Preview
fun App() {
    ModdyLauncherTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NavScreen()
        }
    }
}

val appVersion = "1.0.1-beta"
val appName = "ModdyLauncher"