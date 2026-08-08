package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.database.instance.InstanceData

interface MinecraftRepository {

    suspend fun playVersion(instance: InstanceData, output: (String) -> Unit): Process
}