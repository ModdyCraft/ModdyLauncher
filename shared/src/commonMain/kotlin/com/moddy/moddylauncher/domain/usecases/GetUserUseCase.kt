package com.moddy.moddylauncher.domain.usecases

import com.moddy.moddylauncher.database.user.UserDTO

class GetUserUseCase(
    private val user: UserDTO
) {

    operator fun invoke() = user.getUser()
}