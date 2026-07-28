package com.moddy.moddylauncher.common

import kotlinx.serialization.SerialName

enum class MemoryRam {
    @SerialName("1G")
    G1,

    @SerialName("2G")
    G2,

    @SerialName("4G")
    G4,

    @SerialName("6G")
    G6,

    @SerialName("8G")
    G8
}