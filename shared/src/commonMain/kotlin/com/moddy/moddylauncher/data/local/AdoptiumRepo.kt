package com.moddy.moddylauncher.data.local

import java.io.File

interface AdoptiumRepo {

    suspend fun downloadAdoptium(version: String, output: (String) -> Unit): File
}