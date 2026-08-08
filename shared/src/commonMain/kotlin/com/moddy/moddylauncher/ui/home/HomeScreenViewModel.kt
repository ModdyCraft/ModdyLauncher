package com.moddy.moddylauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moddy.moddylauncher.database.instance.InstanceDTO
import com.moddy.moddylauncher.domain.model.MCVersion
import com.moddy.moddylauncher.domain.usecases.LaunchMinecraftUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val launcher: LaunchMinecraftUseCase,
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

    fun launch() {
        viewModelScope.launch {
            uiState.value.versionSelected?.let { launcher(it.version) }
        }
    }

    fun setVersionSelected(version: MCVersion) {
        _uiState.value = _uiState.value.copy(
            versionSelected = version,
        )
    }

    fun setUserName(userName: String) {
        _uiState.value = _uiState.value.copy(
            userName = userName
        )
    }

    fun deleteInstance(id: Int) {
        instanceManager.deleteInstance(id)
        loadInstances()
    }

}