package com.moddy.moddylauncher.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moddy.moddylauncher.domain.usecases.GetUserUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SplashScreenViewModel(
    private val user: GetUserUseCase,
) : ViewModel() {

    private val _consola = MutableStateFlow("")
    val consola = _consola.asStateFlow()

    init {
        viewModelScope.launch {
            print("Cargando Launcher ...")
            print("Descargando Manifest ...")
            print("[Login] Comprobando si existe un usuario ...")
            val user = user()
            if (user != null) {
                print("[Login] Usuario encontrado")
                print("[Login] Usuario ${user.username} bienvenido")
                print("[Login] Redirigiendo a pantalla de inicio")
            }
            print("[Login] Usuario no existente")
            print("[Login] Redirigiendo a pantalla de Autenticación")
        }
    }

    suspend fun print(text: String) {
        _consola.value += "\n $text"
        delay(250.milliseconds)
    }

}