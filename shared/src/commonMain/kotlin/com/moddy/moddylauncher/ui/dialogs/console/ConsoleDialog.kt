package com.moddy.moddylauncher.ui.dialogs.console

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow

@Composable
fun ConsoleDialog(
    visible: Boolean = false,
    onCloseRequest: () -> Unit,
) {
    DialogWindow(
        onCloseRequest = onCloseRequest,
        content = {
            Text(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                text = "Console"
            )
        },
        visible = visible
    )
}