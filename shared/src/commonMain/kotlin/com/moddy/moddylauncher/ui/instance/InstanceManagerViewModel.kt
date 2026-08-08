package com.moddy.moddylauncher.ui.instance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moddy.moddylauncher.database.instance.InstanceDTO
import com.moddy.moddylauncher.database.instance.InstanceData
import com.moddy.moddylauncher.domain.manifest.Version
import com.moddy.moddylauncher.domain.usecases.ClientType
import com.moddy.moddylauncher.domain.usecases.GetJREListUseCase
import com.moddy.moddylauncher.domain.usecases.GetMinecraftListVersionsUseCase
import com.moddy.moddylauncher.domain.usecases.VersionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InstanceManagerViewModel(
    private val versionList: GetMinecraftListVersionsUseCase,
    private val adoptiumList: GetJREListUseCase,
    private val instanceManager: InstanceDTO
) : ViewModel() {

    private val _uiState = MutableStateFlow(InstanceManagerUiState())
    val uiState: StateFlow<InstanceManagerUiState> = _uiState

    private var versionsA: List<Version> = emptyList()
    private var versionsB: List<String> = emptyList()

    init {
        viewModelScope.launch {
            val listJre = adoptiumList()!!.map { it.toString() }.toMutableList()

            listJre.addFirst("Default")
            versionsA = versionList(uiState.value.versionFilter)
            versionsB = versionsA.map { it.id }

            setNewUiState(versionsB)

            _uiState.value = uiState.value.copy(
                jreList = listJre
            )
        }
    }

    private fun setNewUiState(versions: List<String>) {
        _uiState.value = uiState.value.copy(
            versions = versions,
            version = versions.first(),
            instanceNamePlaceHolder = versions.first()
        )
    }

    fun setInstanceName(name: String) {
        _uiState.value = uiState.value.copy(
            instanceName = name
        )
    }

    fun setJVMArguments(jvmArgs: String) {
        _uiState.value = uiState.value.copy(
            JVMArgs = jvmArgs
        )
    }

    fun setJavaExecutable(javaExecutable: String) {
        _uiState.value = uiState.value.copy(
            javaExecutable = javaExecutable
        )
    }

    fun setInstanceFolder(folder: String) {}

    fun setVersionFilter(filter: VersionType) {
        _uiState.value = uiState.value.copy(
            versionFilter = filter
        )

        viewModelScope.launch {

            val versions = versionList(uiState.value.versionFilter)

            _uiState.value = uiState.value.copy(
                versions = versionsB,
                version = versionsB.first(),
                instanceNamePlaceHolder = versionsB.first()
            )
        }
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

    fun onSavePressed(
        onFinished: () -> Unit,
    ) {
        viewModelScope.launch {

            val instance = InstanceData(
                instanceName = uiState.value.instanceName,
                versionFilter = uiState.value.versionFilter.name,
                version = uiState.value.version,
                JVMARGS = uiState.value.JVMArgs,
                width = uiState.value.width.toLong(),
                height = uiState.value.height.toLong(),
                fullWindow = uiState.value.fullWindow,
                clienteFilter = uiState.value.clientFilter.name,
                javaExec = uiState.value.javaExecutable,
            )

            _uiState.value = uiState.value.copy(
                loading = true
            )

            instanceManager.insertInstance(instance)

            onFinished()
        }
    }
}