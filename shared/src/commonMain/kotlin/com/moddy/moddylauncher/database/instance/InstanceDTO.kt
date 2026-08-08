package com.moddy.moddylauncher.database.instance

import com.moddy.moddylauncher.cache.AppDatabase
import com.moddy.moddylauncher.database.DatabaseDriverFactory

class InstanceDTO(
    driverFactory: DatabaseDriverFactory
) {

    private val driver = driverFactory.createDriver()
    private val databse = AppDatabase(driver)
    private val dbQuery = databse.instanceQueries

    fun insertInstance(instance: InstanceData) {
        val instance = instance.toInstance()
        dbQuery.insertInstance(
            instanceName = instance.instanceName,
            versionFilter = instance.versionFilter,
            version = instance.version,
            directory = instance.directory,
            JVMARGS = instance.JVMARGS,
            width = instance.width,
            height = instance.height,
            fullWindow = instance.fullWindow
        )
    }

    fun updateInstance(instance: InstanceData) {
        val instance = instance.toInstance()
        dbQuery.updateInstance(
            instanceName = instance.instanceName,
            versionFilter = instance.versionFilter,
            version = instance.version,
            directory = instance.directory,
            JVMARGS = instance.JVMARGS,
            width = instance.width,
            height = instance.height,
            fullWindow = instance.fullWindow,
            id = instance.id
        )
    }

    fun getInstances(): List<InstanceData> {
        return dbQuery.getInstances().executeAsList().map { it.toData() }
    }

    fun getInstanceByID(id: Int): InstanceData? {
        return dbQuery.getInstanceByID(id.toLong()).executeAsOneOrNull()?.toData()
    }

    fun deleteInstance(id: Int) {
        dbQuery.deleteInstance(id.toLong())
    }
}