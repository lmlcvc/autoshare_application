package com.riteh.autoshare

import android.app.Application
import com.androidisland.vita.startVita
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startVita()
    }
}