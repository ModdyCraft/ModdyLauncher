package com.moddy.moddylauncher.data.local

interface MinecraftRepository {

    suspend fun playVersion(versionId: String)
}