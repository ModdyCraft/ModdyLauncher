package com.moddy.moddylauncher.ui.instance

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class InstanceManagerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InstanceManagerUiState())
    val uiState: StateFlow<InstanceManagerUiState> = _uiState


}