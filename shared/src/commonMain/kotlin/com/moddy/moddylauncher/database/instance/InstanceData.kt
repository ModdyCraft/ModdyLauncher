package com.moddy.moddylauncher.database.instance

import com.moddy.moddylauncher.cache.Instance

data class InstanceData(
    val id: Int = 0,
    val instanceName: String,
    val versionFilter: String,
    val clienteFilter: String,
    val version: String,
    val directory: String = "",
    val javaExec: String,
    val JVMARGS: String,
    val width: Long,
    val height: Long,
    val fullWindow: Boolean,
) {
    fun toInstance(): Instance {
        return Instance(
            id = id.toLong(),
            instanceName = instanceName,
            versionFilter = versionFilter,
            directory = directory,
            version = version,
            JVMARGS = JVMARGS,
            width = width,
            height = height,
            fullWindow = if (fullWindow) 1 else 0,
            javaExecutable = javaExec,
            clienteFIlter = clienteFilter,
        )
    }
}

fun Instance.toData(): InstanceData {
    return InstanceData(
        id = this.id.toInt(),
        instanceName = this.instanceName,
        versionFilter = this.versionFilter,
        directory = this.directory,
        version = this.version,
        JVMARGS = JVMARGS,
        width = this.width,
        height = this.height,
        fullWindow = this.fullWindow.toInt() != 0,
        javaExec = this.javaExecutable,
        clienteFilter = this.clienteFIlter
    )
}
