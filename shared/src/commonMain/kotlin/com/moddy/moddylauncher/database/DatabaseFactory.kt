package com.moddy.moddylauncher.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.moddy.moddylauncher.cache.AppDatabase

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

class DatabaseFactory : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver = JdbcSqliteDriver(
        url = "jdbc:sqlite:db.db",
        schema = AppDatabase.Schema
    )
}