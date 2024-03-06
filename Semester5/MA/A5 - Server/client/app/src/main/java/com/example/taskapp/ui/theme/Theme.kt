package com.example.taskapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    background = Color.White,
    onPrimary = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    background = Color.White,
    onPrimary = Color.Black
)

val taskBGYellow = Color(0xFFfff69b)
val taskBGBlue = Color(0xFFa1c8e9)

@Composable
fun TaskAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}