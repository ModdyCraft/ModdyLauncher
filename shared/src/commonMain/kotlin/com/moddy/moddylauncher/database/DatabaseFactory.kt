package com.moddy.moddylauncher.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.moddy.moddylauncher.cache.AppDatabase

object DatabaseFactory {
    fun createDriver() = JdbcSqliteDriver(
        url = "jdbc:sqlite:test.db",
        schema = AppDatabase.Schema
    )
}