package com.moddy.moddylauncher

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.moddy.moddylauncher.ui.components.NavBar

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavBar()
    }
}