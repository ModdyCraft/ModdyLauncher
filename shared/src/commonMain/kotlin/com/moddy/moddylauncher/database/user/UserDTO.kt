package com.moddy.moddylauncher.database.user

import com.moddy.moddylauncher.cache.AppDatabase
import com.moddy.moddylauncher.database.DatabaseDriverFactory

class UserDTO(driverFactory: DatabaseDriverFactory) {

    private val driver = driverFactory.createDriver()
    private val databse = AppDatabase(driver)
    private val dbQuery = databse.userQueries

    fun createuser(user: UserData) {
        dbQuery.insertOrReplaceUser(user.username, user.uuid)
        println("inserting user ${user}")
    }

    fun getUser(): UserData? {
        val user = dbQuery.getUser().executeAsOneOrNull()

        return user?.let { UserData(it.username, it.uiid) }
    }

    fun deletUser() {
        dbQuery.deleteUser()
    }
}