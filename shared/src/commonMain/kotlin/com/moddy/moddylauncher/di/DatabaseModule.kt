package com.moddy.moddylauncher.di

import app.cash.sqldelight.db.SqlDriver
import com.moddy.moddylauncher.database.DatabaseFactory
import com.moddy.moddylauncher.database.user.UserDTO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val DatabaseModule = module {
    single<SqlDriver> { DatabaseFactory.createDriver() }

    singleOf(::UserDTO)
}