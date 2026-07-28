package com.moddy.moddylauncher.domain.usecases

import com.moddy.moddylauncher.data.local.MinecraftRepository
import com.moddy.moddylauncher.database.user.UserData
import com.moddy.moddylauncher.database.user.UserDatabase

class LaunchMinecraftUseCase(
    private val database: UserDatabase,
    private val launcher: MinecraftRepository,
) {
    suspend operator fun invoke(version: String, userName: String) {

        val userName = userName.replace(" ", "_")

        val user = database.getuser()

        if (user == null) {
            database.createuser(UserData(userName))
        } else if (user.username != userName) {
            database.createuser(UserData(userName))
        }

        launcher.playVersion(version)
    }
}