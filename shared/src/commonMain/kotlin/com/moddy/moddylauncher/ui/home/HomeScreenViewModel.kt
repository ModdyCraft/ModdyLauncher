package com.moddy.moddylauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moddy.moddylauncher.data.local.MinecraftRepository
import com.moddy.moddylauncher.domain.usecases.GetMinecraftListVersionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val versions: GetMinecraftListVersionsUseCase,
    private val minecraft: MinecraftRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    init {
        viewModelScope.launch {

            val versions = versions()

            _uiState.value = _uiState.value.copy(
                versions = versions,
                versionSelected = versions.first(),
            )
        }
    }

    fun launch() {
        viewModelScope.launch {
            minecraft.playVersion("1.21.11")
        }
    }

    fun setVersionSelected(version: String) {
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