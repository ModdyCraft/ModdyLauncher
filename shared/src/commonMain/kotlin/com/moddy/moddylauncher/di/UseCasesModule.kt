package com.moddy.moddylauncher.di

import com.moddy.moddylauncher.domain.usecases.CreateUserUseCase
import com.moddy.moddylauncher.domain.usecases.GetMinecraftListVersionsUseCase
import com.moddy.moddylauncher.domain.usecases.LaunchMinecraftUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val UseCasesModule = module {
    factoryOf(::GetMinecraftListVersionsUseCase)
    factoryOf(::LaunchMinecraftUseCase)

    factoryOf(::CreateUserUseCase)
}