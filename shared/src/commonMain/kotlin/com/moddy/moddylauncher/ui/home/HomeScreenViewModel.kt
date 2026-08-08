package com.moddy.moddylauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moddy.moddylauncher.database.instance.InstanceDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val instanceManager: InstanceDTO
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    init {
        loadInstances()
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