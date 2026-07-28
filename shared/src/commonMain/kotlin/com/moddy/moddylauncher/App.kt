package com.moddy.moddylauncher

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.moddy.moddylauncher.ui.splash.SplashScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        SplashScreen()
        // AuthScreen()
        // HomeScreen()
    }
}

val appVersion = "1.0.1-beta"
val appName = "ModdyLauncher"