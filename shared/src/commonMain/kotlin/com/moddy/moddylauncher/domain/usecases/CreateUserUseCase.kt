package com.moddy.moddylauncher.domain.usecases

import com.moddy.moddylauncher.database.user.UserDTO
import com.moddy.moddylauncher.database.user.UserData

class CreateUserUseCase(
    private val userDTO: UserDTO
) {

    operator fun invoke(username: String) {
        userDTO.createuser(user = UserData(username))
    }
}