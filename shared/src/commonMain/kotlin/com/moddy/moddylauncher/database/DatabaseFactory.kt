package com.moddy.moddylauncher.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

object DatabaseFactory {
    fun createDriver() = JdbcSqliteDriver(
        url = "jdbc:sqlit:test.db"
    )
}