package com.pilabor.resonance.di

import com.pilabor.resonance.MainViewModel
import com.pilabor.resonance.feature.details.DetailsViewModel
import com.pilabor.resonance.feature.home.HomeViewModel
import com.pilabor.resonance.notification.NotificationHelper
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)

    singleOf(::NotificationHelper)
}