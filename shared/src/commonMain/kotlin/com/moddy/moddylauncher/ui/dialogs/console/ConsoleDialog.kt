package com.moddy.moddylauncher.ui.dialogs.console

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import com.moddy.moddylauncher.ui.ModdyLauncherTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConsoleDialog(
    instanceId: Int,
    onCloseRequest: () -> Unit,
    viewModel: ConsoleDialogViewModel = koinViewModel()
) {

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopGame()
        }
    }

    val text by viewModel.text.collectAsState()

    val lines = remember(text) {
        text.split('\n')
    }

    val listState = rememberLazyListState()

    LaunchedEffect(instanceId) {
        viewModel.launchGame(
            instanceId = instanceId,
            popBack = onCloseRequest
        )
    }

    LaunchedEffect(lines.size) {
        if (lines.isNotEmpty()) {
            listState.scrollToItem(lines.lastIndex)
        }
    }

    DialogWindow(
        onCloseRequest = onCloseRequest,
        title = "Minecraft Console",
        state = rememberDialogState(
            size = DpSize(
                height = 650.dp,
                width = 1000.dp
            )
        )
    ) {
        ModdyLauncherTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF0D1117)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(
                        count = lines.size
                    ) { index ->
                        Text(
                            text = lines[index],
                            color = Color(0xFFD6D6D6),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}