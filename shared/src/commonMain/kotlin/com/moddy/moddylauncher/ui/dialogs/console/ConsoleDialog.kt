package com.moddy.moddylauncher.ui.dialogs.console

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConsoleDialog(
    instanceId: Int,
    visible: Boolean = false,
    onCloseRequest: () -> Unit,
    viewModel: ConsoleDialogViewModel = koinViewModel()
) {

    LaunchedEffect(instanceId) {
        viewModel.launchGame(instanceId, popBack = onCloseRequest)
    }

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