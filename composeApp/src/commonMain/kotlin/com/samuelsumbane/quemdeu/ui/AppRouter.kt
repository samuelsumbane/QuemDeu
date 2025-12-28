package com.samuelsumbane.quemdeu.ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.samuelsumbane.quemdeu.ui.view.HomePageScreen
import com.samuelsumbane.quemdeu.ui.view.TransactionScreen
import com.samuelsumbane.quemdeu.ui.view.UserPageScreen

fun appRouter(navigator: Navigator, page: String) {
    navigator.push(
        item = when (page) {
            PageName.HOME.value -> HomePageScreen()
            PageName.TRANSACTIONS.value -> TransactionScreen()
            else -> UserPageScreen()
        }
    )
}

