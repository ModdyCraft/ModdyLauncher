package com.moddy.moddylauncher.di

import com.moddy.moddylauncher.domain.usecases.GetMinecraftListVersionsUseCase
import org.koin.dsl.module

val UseCasesModule = module {
    factory { GetMinecraftListVersionsUseCase(get()) }
}