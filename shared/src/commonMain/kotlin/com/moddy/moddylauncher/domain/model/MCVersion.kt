package com.moddy.moddylauncher.domain.model

/**
 * Este modelo se usa para poder hacer las listas de versiones y determinar si la version se encuentra instalada o no
 * con un simple procedimiento
 */
data class MCVersion(
    val version: String,
    val isInstalled: Boolean = false,
)
