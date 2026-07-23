package com.moddy.moddylauncher.ui.instance

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun InstancesScreen() {
    val insatnces = emptyList<String>()
    LazyHorizontalGrid(
        rows = GridCells.Adaptive(48.dp)
    ) {

    }
}