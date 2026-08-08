package com.moddy.moddylauncher.ui.instance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.moddy.moddylauncher.ui.components.DropDownMenuField
import com.moddy.moddylauncher.ui.components.LOADINGSPLASH
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InstanceManagerScreen(
    popBack: () -> Unit,
    instance: Int?,
    viewModel: InstanceManagerViewModel = koinViewModel(),
) {

    LaunchedEffect(instance) {
        viewModel.loadInstance(instance)
    }

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Row {
            Box(Modifier.size(150.dp).background(Color.Blue))
            Spacer(modifier = Modifier.size(14.dp))
            Column(
                modifier = Modifier.height(150.dp).padding(vertical = 6.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Instance Name
                OutlinedTextField(
                    value = uiState.instanceName,
                    onValueChange = viewModel::setInstanceName,
                    label = {
                        Text("Instance Name")
                    },
                    placeholder = { Text(uiState.instanceNamePlaceHolder) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = uiState.instancePath,
                    onValueChange = viewModel::setInstanceFolder,
                    label = {
                        Text("Directory Path")
                    },
                    enabled = false,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Filtros
        Row {
            DropDownMenuField(
                options = uiState.versionFilters.map { it.name },
                selectedOption = uiState.versionFilter.name,
                selected = { viewModel.setVersionFilter(it) },
                modifier = Modifier.width(150.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = uiState.clientFilter.name,
                onValueChange = {},
                modifier = Modifier.width(150.dp),
                enabled = false,
            )
        }

        // Version
        Spacer(Modifier.height(12.dp))

        DropDownMenuField(
            options = uiState.versions,
            selectedOption = uiState.version,
            selected = { viewModel.setVersion(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        DropDownMenuField(
            options = uiState.jreList,
            selectedOption = uiState.javaExecutable,
            selected = {
                viewModel.setJavaExecutable(it)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.JVMArgs,
            onValueChange = viewModel::setJVMArguments,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Windows Size
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = uiState.height,
                onValueChange = viewModel::setWindowHeight,
                modifier = Modifier.width(150.dp),
                enabled = !uiState.fullWindow,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = uiState.width,
                onValueChange = viewModel::setWindowWidth,
                modifier = Modifier.width(150.dp),
                enabled = !uiState.fullWindow,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,

            )
            Spacer(Modifier.width(16.dp))
            Checkbox(
                checked = uiState.fullWindow,
                onCheckedChange = viewModel::toggleWindowFull,
            )
            Spacer(Modifier.width(2.dp))
            Text("Full Size")
        }

        Spacer(Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                onClick = popBack
            ) {
                Text(text = "CANCEL")
            }
            Spacer(Modifier.width(16.dp))
            OutlinedButton(
                onClick = {
                    viewModel.onSavePressed(
                        onFinished = popBack
                    )
                },
                enabled = uiState.versions.isNotEmpty(),
            ) {
                Text(text = "SAVE")
            }
        }
    }

    LOADINGSPLASH(uiState.loading)
}