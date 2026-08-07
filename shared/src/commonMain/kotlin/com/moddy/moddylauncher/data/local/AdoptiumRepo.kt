package com.moddy.moddylauncher.data.local

interface AdoptiumRepo {

    suspend fun downloadAdoptium(version: String)
}