package com.moddy.moddylauncher.di

import app.cash.sqldelight.db.SqlDriver
import com.moddy.moddylauncher.database.DatabaseFactory
import org.koin.dsl.module

val DatabaseModule = module {
    factory<SqlDriver> { DatabaseFactory.createDriver() }
}