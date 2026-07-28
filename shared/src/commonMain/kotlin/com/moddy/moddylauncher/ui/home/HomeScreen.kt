package com.moddy.moddylauncher.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moddy.moddylauncher.ui.components.MCVersionDropDown
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
) {

    val uiState: HomeScreenUiState by viewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        MCVersionDropDown(
            options = uiState.versions,
            selectedOption = uiState.versionSelected,
            onOptionSelected = viewModel::setVersionSelected
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            onClick = viewModel::launch,
        ) {
            Text("PLAY")
        }
    }
}