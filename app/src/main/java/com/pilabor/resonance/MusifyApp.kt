package com.codewithfk.musify_android

import android.app.Application
import com.codewithfk.musify_android.data.helper.MusifyNotificationHelper
import com.codewithfk.musify_android.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module

class MusifyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MusifyApp)
            modules(defaultModule, NetworkModule().module)
        }
        MusifyNotificationHelper.createNotificationChannel(this)
    }
}