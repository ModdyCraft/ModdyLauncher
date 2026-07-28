package com.moddy.moddylauncher.di

import com.moddy.moddylauncher.domain.usecases.GetMinecraftListVersionsUseCase
import com.moddy.moddylauncher.domain.usecases.LaunchMinecraftUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val UseCasesModule = module {
    factory { GetMinecraftListVersionsUseCase(get()) }
    factoryOf(::LaunchMinecraftUseCase)
}