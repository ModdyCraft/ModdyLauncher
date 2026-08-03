package com.moddy.moddylauncher.ui.components

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moddylauncher.shared.generated.resources.INSTANCE_DEFAULT
import moddylauncher.shared.generated.resources.Res
import org.jetbrains.compose.resources.painterResource

@Composable
fun VersionCard(
    onClick: () -> Unit = {}
) {
    ContextMenuArea(
        items = {
            listOf()
        }
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(resource = Res.drawable.INSTANCE_DEFAULT),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            Text("VERSION IDENTIFIER", softWrap = true)
        }
    }
}