package com.moddy.moddylauncher.di

import com.moddy.moddylauncher.domain.usecases.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val UseCasesModule = module {
    factoryOf(::GetMinecraftListVersionsUseCase)
    factoryOf(::LaunchMinecraftUseCase)

    factoryOf(::CreateUserUseCase)
    factoryOf(::GetUserUseCase)
    factoryOf(::DownloadManifestUseCase)
}