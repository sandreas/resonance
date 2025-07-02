package com.pilabor.resonance

import android.app.Application
import com.pilabor.resonance.di.appModule
import com.pilabor.resonance.helper.NotificationHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp: Application() {
    companion object {
        private var instance: MainApp? = null

        fun getContext(): MainApp {
            return instance!!
        }
    }

    override fun onCreate() {
        instance = this

        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(appModule)
        }
        NotificationHelper.createNotificationChannel(this)
    }




}