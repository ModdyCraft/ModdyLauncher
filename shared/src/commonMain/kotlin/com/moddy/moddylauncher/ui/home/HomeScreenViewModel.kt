package com.moddy.moddylauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moddy.moddylauncher.domain.model.MCVersion
import com.moddy.moddylauncher.domain.usecases.GetMinecraftListVersionsUseCase
import com.moddy.moddylauncher.domain.usecases.LaunchMinecraftUseCase
import com.moddy.moddylauncher.domain.usecases.VersionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val versions: GetMinecraftListVersionsUseCase,
    private val launcher: LaunchMinecraftUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    init {
        viewModelScope.launch {

            val versions = versions(VersionType.release)

            _uiState.value = _uiState.value.copy(
                versions = versions,
                versionSelected = versions.first(),
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

}