package com.pilabor.resonance.di

import com.pilabor.resonance.MainViewModel
import com.pilabor.resonance.data.SettingsStorage
import com.pilabor.resonance.data.helper.NotificationHelper
import com.pilabor.resonance.ui.feature.home.HomeViewModel
import com.pilabor.resonance.ui.feature.playsong.PlaySongViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    singleOf(::SettingsStorage)
    singleOf(::NotificationHelper)
    viewModelOf(::MainViewModel)
    viewModelOf(::PlaySongViewModel)
    viewModelOf(::HomeViewModel)
}