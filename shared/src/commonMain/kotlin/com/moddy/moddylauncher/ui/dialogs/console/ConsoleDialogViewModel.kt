package com.moddy.moddylauncher.ui.dialogs.console

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moddy.moddylauncher.database.instance.InstanceDTO
import com.moddy.moddylauncher.database.instance.InstanceData
import com.moddy.moddylauncher.domain.usecases.LaunchMinecraftUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConsoleDialogViewModel(
    private val launch: LaunchMinecraftUseCase,
    private val instances: InstanceDTO
) : ViewModel() {

    private var _text = MutableStateFlow("")
    val text = _text.asStateFlow()

    private var instance = MutableStateFlow<InstanceData?>(null)

    init {
        println("Hello world!")
        println("This is my Console")
    }

    fun launchGame(instanceId: Int, popBack: () -> Unit) {
        println("[INSTANCE]: ID: $instanceId")
        println("[INSTANCE]: FETCHING ...")
        instance.value = instances.getInstanceByID(instanceId)
        println("[INSTANCE]: Fetched")
        println("[INSTANCE]: Verifying Instance")

        if (instance.value == null) {
            println("[INSTANCE]: Instance not found")
            println("[INSTANCE]: Returning to the instance manager")
            popBack()
        }

        instance.value?.let { instance ->
            println("[INSTANCE]: Instance found")
            println("[INSTANCE]: Instance Name: ${instance.instanceName}")
            println("[INSTANCE]: Instance DATA: $instance")
            println("[FETCHING-MINECRAFT]: Minecraft VERSION -> ${instance.version}")
            viewModelScope.launch {
                launch(instance, output = { println(it) })
            }
        }
    }

    fun println(t: String) {
        _text.value = text.value.plus("\n$t ")
    }
}