package com.moddy.moddylauncher.di

import com.moddy.moddylauncher.database.DatabaseDriverFactory
import com.moddy.moddylauncher.database.DatabaseFactory
import com.moddy.moddylauncher.database.instance.InstanceDTO
import com.moddy.moddylauncher.database.user.UserDTO
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val DatabaseModule = module {
    factory<DatabaseDriverFactory> { DatabaseFactory() }

    factoryOf(::UserDTO)

    factoryOf(::InstanceDTO)
}