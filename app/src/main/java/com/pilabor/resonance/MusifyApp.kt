package com.pilabor.resonance

import android.app.Application
import com.pilabor.resonance.data.helper.ResonanceNotificationHelper
import com.pilabor.resonance.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import org.koin.ksp.generated.module

class ResonanceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ResonanceApp)
            modules(defaultModule, NetworkModule().module)
        }
        ResonanceNotificationHelper.createNotificationChannel(this)
    }
}