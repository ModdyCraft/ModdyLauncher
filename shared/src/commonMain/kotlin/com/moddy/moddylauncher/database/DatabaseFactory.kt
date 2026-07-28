package com.moddy.moddylauncher.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

class DatabaseFactory {
    fun driver() = JdbcSqliteDriver(
        url = "jdbc:sqlit:test.db"
    )
}