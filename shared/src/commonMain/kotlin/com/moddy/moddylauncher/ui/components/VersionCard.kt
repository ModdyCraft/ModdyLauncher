package com.moddy.moddylauncher.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.moddy.moddylauncher.database.instance.InstanceData
import com.moddy.moddylauncher.icons.play_arrow
import moddylauncher.shared.generated.resources.INSTANCE_DEFAULT
import moddylauncher.shared.generated.resources.Res
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VersionCard(
    instance: InstanceData,
    onDeletePressed: () -> Unit,
    onEditPressed: () -> Unit,
    onClick: () -> Unit = {},
) {

    var playable by remember { mutableStateOf(false) }

    ContextMenuArea(
        items = {
            listOf(
                ContextMenuItem("Play") {

                },
                ContextMenuItem("Edit Instance") {
                    onEditPressed()
                },
                ContextMenuItem("Delete Instance") {
                    onDeletePressed()
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .size(150.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(onClick = onClick)
                .onPointerEvent(
                    PointerEventType.Enter,
                    onEvent = { playable = true },
                )
                .onPointerEvent(PointerEventType.Exit) { playable = false }
                .pointerHoverIcon(
                    icon = PointerIcon.Hand
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(resource = Res.drawable.INSTANCE_DEFAULT),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            if (playable) {
                Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.7f)))
                Icon(
                    imageVector = play_arrow,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(70.dp)
                )
            } else {
                Text(instance.instanceName, softWrap = true)
            }
        }
    }
}