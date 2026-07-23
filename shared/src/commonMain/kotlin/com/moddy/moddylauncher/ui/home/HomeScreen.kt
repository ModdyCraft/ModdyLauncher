package com.moddy.moddylauncher.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Nickname") },
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            onClick = {},
        ) {
            Text("PLAY")
        }
    }
}