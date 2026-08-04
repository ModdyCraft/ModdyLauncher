package com.moddy.moddylauncher.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moddy.moddylauncher.ui.Orange
import com.moddy.moddylauncher.ui.components.EmptyVersionCard
import com.moddy.moddylauncher.ui.components.VersionCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .height(45.dp)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()

                    drawLine(
                        color = Orange,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                }
                .padding(horizontal = 20.dp)
        ) {
            Text(
                "Jugar Version"
            )

            Spacer(Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "HOLA JUGADOR DE MNO"
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(Color.Gray, shape = CircleShape)
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 4.dp),
            columns = GridCells.Adaptive(minSize = 150.dp),
            content = {
                item {
                    EmptyVersionCard(
                        onClick = {}
                    )
                }
                items(5) {
                    VersionCard()
                }
            }
        )
    }
}