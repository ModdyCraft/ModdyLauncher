package com.moddy.moddylauncher.di

import com.moddy.moddylauncher.ui.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelsModule = module {
    viewModelOf(::HomeScreenViewModel)
}