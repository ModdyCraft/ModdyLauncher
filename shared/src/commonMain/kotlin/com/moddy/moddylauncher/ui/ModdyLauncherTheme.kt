package com.moddy.moddylauncher.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ==============================
// Colores
// ==============================

val DarkBlue = Color(0xFF0B1220)
val DarkBlueSecondary = Color(0xFF111C2E)
val BlueSurface = Color(0xFF17243A)

val Orange = Color(0xFFFF8A00)
val OrangeDark = Color(0xFFD96F00)

val White = Color(0xFFF5F7FA)
val Gray = Color(0xFFAAB4C3)

val ErrorRed = Color(0xFFFF5252)


// ==============================
// Color Scheme
// ==============================

private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    onPrimary = Color.Black,

    primaryContainer = OrangeDark,
    onPrimaryContainer = White,

    secondary = Orange,
    onSecondary = Color.Black,

    background = DarkBlue,
    onBackground = White,

    surface = DarkBlueSecondary,
    onSurface = White,

    surfaceVariant = BlueSurface,
    onSurfaceVariant = Gray,

    error = ErrorRed,
    onError = White
)


// ==============================
// Theme
// ==============================

@Composable
fun ModdyLauncherTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography(),
        content = content,
    )
}