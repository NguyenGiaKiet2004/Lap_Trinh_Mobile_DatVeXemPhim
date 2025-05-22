package com.example.appmoview.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorScheme = lightColorScheme(
    primary = Yellow,
    secondary = Yellow,
    tertiary = Yellow,
    background = Color(0xFF000000), // nền đen
    surface = Color(0xFF121212),   // nền bề mặt đậm hơn
    onPrimary = Color.White,       // màu chữ trên nút primary
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,    // màu chữ trên nền
    onSurface = Color.White        // màu chữ trên surface
)

@Composable
fun SystemTheme(
    darkTheme: Boolean = false, // LUÔN dùng theme sáng
    dynamicColor: Boolean = false, // Không dùng dynamic color
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

