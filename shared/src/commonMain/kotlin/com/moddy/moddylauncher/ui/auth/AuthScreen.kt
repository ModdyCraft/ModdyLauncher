package com.moddy.moddylauncher.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    navTo: () -> Unit,
    viewModel: AuthScreenViewModel = koinViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.username,
            onValueChange = viewModel::setUsername,
            label = { Text("Username") },
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.createUser(
                    completed = navTo
                )
            },
            enabled = !uiState.loading
        ) {
            Text("CREAR USUARIO")
        }
    }
}