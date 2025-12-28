package com.samuelsumbane.quemdeu

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator

@Composable
expect fun isMobilePortrait(): Boolean

/**
 * This component will be only for Mobile
 *
 */

@Composable
expect fun BottomNav(
    navigator: Navigator,
    activePage: String
)