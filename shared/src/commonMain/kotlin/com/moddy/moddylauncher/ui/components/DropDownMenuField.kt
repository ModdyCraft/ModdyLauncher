package com.moddy.moddylauncher.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastForEachIndexed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuField(
    options: List<String>,
    selected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    val options = options.ifEmpty { listOf("Default") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = options[selectedIndex],
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
            options.fastForEachIndexed { i, s ->
                DropdownMenuItem(
                    text = { Text(s) },
                    onClick = {
                        selectedIndex = i
                        selected(i)
                        expanded = false
                    }
                )
            }
        }
    }
}