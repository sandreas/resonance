package com.pilabor.resonance

import android.app.Application
import com.pilabor.resonance.data.helper.NotificationHelper
import com.pilabor.resonance.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule


class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(defaultModule, appModule)
        }
        NotificationHelper.createNotificationChannel(this)
    }
}