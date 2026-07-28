package com.moddy.moddylauncher.database.user

import app.cash.sqldelight.db.SqlDriver
import com.moddy.moddylauncher.cache.AppDatabase

internal class UserDatabase(driver: SqlDriver) {
    private val databse = AppDatabase(driver)
    private val dbQuery = databse.userQueries

    internal fun createuser(user: UserData) {
        dbQuery.insertOrReplaceUser(user.username, user.uuid)
    }

    internal fun getuser(): UserData {
        val user = dbQuery.getUser().executeAsOne()

        return UserData(user.username, user.uiid)
    }

    internal fun deletUser() {
        dbQuery.deleteUser()
    }
}