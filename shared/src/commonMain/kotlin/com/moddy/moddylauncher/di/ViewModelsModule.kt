package com.moddy.moddylauncher.di

import com.moddy.moddylauncher.ui.auth.AuthScreenViewModel
import com.moddy.moddylauncher.ui.home.HomeScreenViewModel
import com.moddy.moddylauncher.ui.splash.SplashScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelsModule = module {
    viewModelOf(::HomeScreenViewModel)

    viewModelOf(::AuthScreenViewModel)
    viewModelOf(::SplashScreenViewModel)
}