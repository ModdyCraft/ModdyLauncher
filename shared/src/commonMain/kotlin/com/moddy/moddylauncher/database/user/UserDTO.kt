package com.moddy.moddylauncher.database.user

import app.cash.sqldelight.db.SqlDriver
import com.moddy.moddylauncher.cache.AppDatabase

class UserDTO(driver: SqlDriver) {
    private val databse = AppDatabase(driver)
    private val dbQuery = databse.userQueries

    internal fun createuser(user: UserData) {
        dbQuery.insertOrReplaceUser(user.username, user.uuid)
    }

    internal fun getUser(): UserData? {
        val user = dbQuery.getUser().executeAsOneOrNull()

        return user?.let { UserData(it.username, it.uiid) }
    }

    internal fun deletUser() {
        dbQuery.deleteUser()
    }
}