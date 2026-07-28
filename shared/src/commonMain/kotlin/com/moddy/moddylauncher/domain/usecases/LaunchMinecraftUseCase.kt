package com.moddy.moddylauncher.domain.usecases

import com.moddy.moddylauncher.data.local.MinecraftRepository
import com.moddy.moddylauncher.database.user.UserDTO
import com.moddy.moddylauncher.database.user.UserData

class LaunchMinecraftUseCase(
    private val database: UserDTO,
    private val launcher: MinecraftRepository,
) {
    suspend operator fun invoke(version: String, userName: String) {

        val userName = userName.replace(" ", "_")

        val user = database.getUser()

        if (user == null) {
            database.createuser(UserData(userName))
        } else if (user.username != userName) {
            database.createuser(user.copy(username = userName))
        }

        launcher.playVersion(version)
    }
}