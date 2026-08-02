package com.moddy.moddylauncher.ui.instance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InstanceManagerScreen(
) {
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
                OutlinedTextField(
                    value = "ASD",
                    onValueChange = {},
                    label = {
                        Text("Instance Name")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = "ASD",
                    onValueChange = {},
                    label = {
                        Text("Directory Path")
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Filtros
        Row {
            OutlinedTextField(
                value = "ASD",
                onValueChange = {},
                modifier = Modifier.width(150.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = "ASD",
                onValueChange = {},
                modifier = Modifier.width(150.dp),
            )
        }

        // Version
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = "26.2",
            onValueChange = {},
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = "26.2",
            onValueChange = {},
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = "26.2",
            onValueChange = {},
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Windows Size
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = "ASD",
                onValueChange = {},
                modifier = Modifier.width(150.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = "ASD",
                onValueChange = {},
                modifier = Modifier.width(150.dp),
            )
            Spacer(Modifier.width(16.dp))
            Checkbox(
                checked = false,
                onCheckedChange = {},
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
                onClick = {}
            ) {
                Text(text = "CANCEL")
            }
            Spacer(Modifier.width(16.dp))
            OutlinedButton(
                onClick = {}
            ) {
                Text(text = "SAVE")
            }
        }
    }
}