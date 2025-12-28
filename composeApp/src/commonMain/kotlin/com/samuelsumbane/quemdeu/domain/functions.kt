package com.samuelsumbane.quemdeu.domain

fun isAndroid(): Boolean {
    return try {
        Class.forName("android.app.Activity")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

fun isDesktop(): Boolean = !isAndroid()