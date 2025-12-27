package com.samuelsumbane.quemdeu.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(
    darkMode: Boolean,
    content: @Composable () -> Unit
) {
    val scheme = if (darkMode) {
        darkColorScheme(
            primary = Color.Red,
            onPrimary = Color.White,
            secondary = Color.LightGray,
            onSecondary = Color.White,
        )
    } else {
        lightColorScheme(
            primary = Color.Red,
            onPrimary = Color.White,
            secondary = Color.DarkGray,
            onSecondary = Color.White,
        )
    }

    MaterialTheme(
      colorScheme = scheme,
      content = content
    )
}