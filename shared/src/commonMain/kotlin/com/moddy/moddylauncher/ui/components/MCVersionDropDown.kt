package com.moddy.moddylauncher.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.moddy.moddylauncher.domain.model.MCVersion
import com.moddy.moddylauncher.icons.download
import com.moddy.moddylauncher.icons.download_done

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MCVersionDropDown(
    options: List<MCVersion>,
    selectedOption: MCVersion?,
    onOptionSelected: (MCVersion) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption?.version ?: "",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = modifier.menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.version) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = if (option.isInstalled) download_done else download,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}