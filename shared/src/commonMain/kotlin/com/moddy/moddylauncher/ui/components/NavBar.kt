package com.moddy.moddylauncher.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed

@Composable
fun NavBar() {
    val navList = listOf("Instances", "Console", "Auth", "Settings")

    Row(
        modifier = Modifier.fillMaxWidth().height(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        navList.fastForEachIndexed { i, nav ->
            Text(nav)
            if (i >= navList.lastIndex) {
                VerticalDivider()
            }
        }
    }
}