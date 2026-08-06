package com.moddy.moddylauncher.ui.instance

import androidx.lifecycle.ViewModel
import com.moddy.moddylauncher.domain.usecases.ClientType
import com.moddy.moddylauncher.domain.usecases.VersionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InstanceManagerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InstanceManagerUiState())
    val uiState: StateFlow<InstanceManagerUiState> = _uiState

    fun setInstanceName(name: String) {
        _uiState.value = uiState.value.copy(
            instanceName = name
        )
    }

    fun setInstanceFolder(folder: String) {}

    fun setVersionFilter(filter: VersionType) {
        _uiState.value = uiState.value.copy(
            versionFilter = filter
        )
    }

    fun setVersionFilter(filter: ClientType) {
        _uiState.value = uiState.value.copy(
            clientFilter = filter
        )
    }

    fun setVersion(version: String) {
        _uiState.value = uiState.value.copy(
            version = version
        )
    }

    fun setWindowHeight(height: String) {
        val height = height.filter { it.isDigit() }

        _uiState.value = uiState.value.copy(
            height = height
        )
    }

    fun setWindowWidth(width: String) {
        val width = width.filter { it.isDigit() }

        _uiState.value = uiState.value.copy(
            width = width
        )
    }

    fun toggleWindowFull(enabled: Boolean) {
        _uiState.value = uiState.value.copy(
            fullWindow = enabled
        )
    }
}