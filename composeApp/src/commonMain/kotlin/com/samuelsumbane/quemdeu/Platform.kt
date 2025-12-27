package com.samuelsumbane.quemdeu

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform