package com.moddy.moddylauncher.di

import app.cash.sqldelight.db.SqlDriver
import com.moddy.moddylauncher.database.DatabaseFactory
import com.moddy.moddylauncher.database.user.UserDatabase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val DatabaseModule = module {
    factory<SqlDriver> { DatabaseFactory.createDriver() }

    factoryOf(::UserDatabase)
}