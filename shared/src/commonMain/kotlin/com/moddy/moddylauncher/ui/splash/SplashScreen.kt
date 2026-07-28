package com.moddy.moddylauncher.ui.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.moddy.moddylauncher.ui.navigation.Screen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    navTo: (Screen) -> Unit,
    viewModel: SplashScreenViewModel = koinViewModel()
) {

    val text by viewModel.consola.collectAsState(initial = "")
    val navTo by viewModel.navTo.collectAsState()

    Text(
        text = text,
        modifier = Modifier.fillMaxSize(),
    )

    navTo?.let { navTo(it) }
}