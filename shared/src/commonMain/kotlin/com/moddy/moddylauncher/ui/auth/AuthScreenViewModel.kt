package com.moddy.moddylauncher.ui.auth

import androidx.lifecycle.ViewModel
import com.moddy.moddylauncher.domain.usecases.CreateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthScreenViewModel(
    private val createUser: CreateUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthScreenUiState())
    val uiState: StateFlow<AuthScreenUiState> = _uiState

    fun createUser() {
        createUser(uiState.value.username.replace(" ", "_".take(12)))
        _uiState.value = _uiState.value.copy(
            loading = true
        )
    }

    fun setUsername(username: String) {
        _uiState.value = _uiState.value.copy(
            username = username
        )
    }
}