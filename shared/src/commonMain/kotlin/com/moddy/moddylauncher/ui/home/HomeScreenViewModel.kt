package com.moddy.moddylauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moddy.moddylauncher.data.local.MinecraftRepository
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val minecraft: MinecraftRepository
) : ViewModel() {

    fun launch() {
        viewModelScope.launch {
            minecraft.playVersion("26.2")
        }
    }

}