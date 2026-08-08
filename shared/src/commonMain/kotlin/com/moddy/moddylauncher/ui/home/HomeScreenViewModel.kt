package com.moddy.moddylauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moddy.moddylauncher.database.instance.InstanceDTO
import com.moddy.moddylauncher.database.user.UserData
import com.moddy.moddylauncher.domain.usecases.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val user: GetUserUseCase,
    private val instanceManager: InstanceDTO
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    private var userData: UserData? = null

    init {
        loadInstances()
        viewModelScope.launch {
            userData = user()
            _uiState.update {
                it.copy(
                    userName = userData?.username!!
                )
            }
        }
    }

    fun loadInstances() {
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                instances = instanceManager.getInstances()
            )
        }
    }

    fun deleteInstance(id: Int) {
        instanceManager.deleteInstance(id)
        loadInstances()
    }

}