package com.moddy.moddylauncher.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavBar(
    content: @Composable BoxScope.() -> Unit
) {
    val navList = listOf("Instances", "Console", "Auth", "Settings")

    Row(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth().height(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            navList.fastForEachIndexed { i, nav ->
                Text(
                    nav,
                    style = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center),
                    modifier = Modifier
                        .clickable {}
                        .weight(1f)
                        .fillMaxHeight()
                )
                if (i <= navList.lastIndex) {
                    VerticalDivider(modifier = Modifier.fillMaxHeight())
                }
            }
        }
        Box(Modifier.fillMaxSize(), content = content)
    }
}