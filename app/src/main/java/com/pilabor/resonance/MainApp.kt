package com.pilabor.resonance

import android.app.Application
import com.pilabor.resonance.di.appModule
import com.pilabor.resonance.notification.NotificationHelper
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

        /*
        // Debug strange error messages
        // see https://wh0.github.io/2020/08/12/closeguard.html
        StrictMode.setVmPolicy(
            VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build()
        )
        */
        startKoin {
            androidContext(this@MainApp)
            modules(appModule)
        }
        NotificationHelper.createNotificationChannel(this)

    }
}