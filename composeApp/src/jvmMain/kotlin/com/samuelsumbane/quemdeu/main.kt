package com.samuelsumbane.quemdeu

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import com.samuelsumbane.quemdeu.ui.view.TransactionScreen
import org.koin.core.context.startKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "QuemDeu",
        alwaysOnTop = true,
        state = WindowState(
            size = DpSize(300.dp, 350.dp)
        )
    ) {
        startKoin {
            modules(appModule)
        }
        Navigator(AppScreen())
    }
}


/**
 * On desktop, the app will always in portrait
 */
@Composable
actual fun isMobilePortrait() = true

@Composable
actual fun BottomNav(
    navigator: Navigator,
    activePage: String,
) {
    /**
     * On the desktop, this function will not do anything
     */
}