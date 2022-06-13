package com.riteh.autoshare

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.androidisland.vita.startVita

class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        startVita()
        context = applicationContext
    }
}