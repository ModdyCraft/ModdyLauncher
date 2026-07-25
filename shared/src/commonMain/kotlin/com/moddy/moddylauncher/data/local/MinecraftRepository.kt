package com.moddy.moddylauncher.data.local

interface MinecraftRepository {

    suspend fun launchVersion(versionId: String)


}