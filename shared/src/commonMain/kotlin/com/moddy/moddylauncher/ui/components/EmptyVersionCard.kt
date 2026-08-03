package com.moddy.moddylauncher.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moddy.moddylauncher.icons.add_2

@Composable
fun EmptyVersionCard() {
    Box(
        modifier = Modifier
            .size(150.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = add_2,
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}