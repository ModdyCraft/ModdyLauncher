package com.moddy.moddylauncher.di

import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration

fun InitKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(AppModule, ViewModelsModule)
    }
}