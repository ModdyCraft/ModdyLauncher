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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InstanceManagerViewModel(
    private val versionList: GetMinecraftListVersionsUseCase,
    private val adoptiumList: GetJREListUseCase,
    private val instanceManager: InstanceDTO
) : ViewModel() {

    private val _uiState = MutableStateFlow(InstanceManagerUiState())
    val uiState: StateFlow<InstanceManagerUiState> = _uiState.asStateFlow()

    private var versionsA: List<Version> = emptyList()
    private var instanceG: InstanceData? = null

    private fun loadInitialData(onFinished: () -> Unit = {}) {
        viewModelScope.launch {
            val jreList = buildList {
                add("Default")
                addAll(adoptiumList().orEmpty().map { it.toString() })
            }

            _uiState.update {
                it.copy(
                    jreList = jreList
                )
            }

            loadVersions()
            onFinished()
        }
    }

    fun loadInstance(id: Int?) {
        _uiState.update {
            it.copy(loading = false)
        }

        loadInitialData {
            viewModelScope.launch {
                instanceG = null

                val instance = id?.let(instanceManager::getInstanceByID)
                    ?: return@launch

                instanceG = instance

                val state = _uiState.value
                val versionFilter = state.versionFilters
                    .find { it.name == instance.versionFilter }
                    ?: VersionType.release

                val versions = uiState.value.versions.toMutableList()
                versions.addFirst(instance.version)

                _uiState.update {
                    it.copy(
                        instanceName = instance.instanceName,
                        width = instance.width.toString(),
                        height = instance.height.toString(),
                        versionFilter = versionFilter,
                        versions = versions,
                        version = instance.version,
                        javaExecutable = instance.javaExec,
                        JVMArgs = instance.JVMARGS,
                        fullWindow = instance.fullWindow,
                        clientFilter = state.clientFilter
                    )
                }
            }
        }
    }

    private suspend fun loadVersions() {
        val filter = _uiState.value.versionFilter
        val versions = versionList(filter)

        versionsA = versions

        val versionIds = versions.map { it.id }

        _uiState.update {
            it.copy(
                versions = versionIds,
                version = it.version.takeIf(versionIds::contains)
                    ?: versionIds.firstOrNull().orEmpty(),
                instanceNamePlaceHolder = versionIds.firstOrNull().orEmpty()
            )
        }
    }

    fun setInstanceName(name: String) {
        _uiState.update {
            it.copy(instanceName = name)
        }
    }

    fun setJVMArguments(jvmArgs: String) {
        _uiState.update {
            it.copy(JVMArgs = jvmArgs)
        }
    }

    fun setJavaExecutable(javaExecutable: String) {
        _uiState.update {
            it.copy(javaExecutable = javaExecutable)
        }
    }

    fun setInstanceFolder(folder: String) {
    }

    fun setVersionFilter(filter: VersionType) {
        _uiState.update {
            it.copy(versionFilter = filter)
        }

        viewModelScope.launch {
            loadVersions()
        }
    }

    fun setVersionFilter(filter: ClientType) {
        _uiState.update {
            it.copy(clientFilter = filter)
        }
    }

    fun setVersion(version: String) {
        _uiState.update {
            it.copy(version = version, instanceNamePlaceHolder = version)
        }
    }

    fun setWindowHeight(height: String) {
        _uiState.update {
            it.copy(
                height = height.filter(Char::isDigit)
            )
        }
    }

    fun setWindowWidth(width: String) {
        _uiState.update {
            it.copy(
                width = width.filter(Char::isDigit)
            )
        }
    }

    fun toggleWindowFull(enabled: Boolean) {
        _uiState.update {
            it.copy(fullWindow = enabled)
        }
    }

    fun onSavePressed(
        onFinished: () -> Unit
    ) {
        _uiState.update {
            it.copy(loading = true)
        }

        viewModelScope.launch {

            val state = _uiState.value

            val instance = instanceG?.copy(
                instanceName = state.instanceName
                    .ifEmpty { state.instanceNamePlaceHolder },
                versionFilter = state.versionFilter.name,
                version = state.version,
                JVMARGS = state.JVMArgs,
                width = state.width.toLongOrNull() ?: 0L,
                height = state.height.toLongOrNull() ?: 0L,
                fullWindow = state.fullWindow,
                clienteFilter = state.clientFilter.name,
                javaExec = state.javaExecutable
            ) ?: InstanceData(
                instanceName = state.instanceName
                    .ifEmpty { state.instanceNamePlaceHolder },
                versionFilter = state.versionFilter.name,
                version = state.version,
                JVMARGS = state.JVMArgs,
                width = state.width.toLongOrNull() ?: 0L,
                height = state.height.toLongOrNull() ?: 0L,
                fullWindow = state.fullWindow,
                clienteFilter = state.clientFilter.name,
                javaExec = state.javaExecutable
            )

            if (instanceG == null) {
                instanceManager.insertInstance(instance)
            } else {
                instanceManager.updateInstance(instance)
            }

            instanceG = instance
            onFinished()
        }
    }
}