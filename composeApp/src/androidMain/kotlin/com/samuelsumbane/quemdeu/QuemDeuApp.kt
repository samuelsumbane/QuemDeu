package com.samuelsumbane.quemdeu

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class QuemDeuApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@QuemDeuApp)
            modules(appModule)
        }
    }
}