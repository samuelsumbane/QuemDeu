package com.samuelsumbane.quemdeu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            Navigator(AppScreen())
        }
    }
}


@Composable
actual fun isMobilePortrait(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
}

@Composable
actual fun BottomNav(
    navigator: Navigator,
    activePage: String,
) {
    if (isMobilePortrait()) BottomAppBarPrincipal(navigator, activePage)
}