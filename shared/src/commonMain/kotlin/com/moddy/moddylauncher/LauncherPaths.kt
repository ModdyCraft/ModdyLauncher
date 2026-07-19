package com.moddy.moddylauncher

import java.io.File

object LauncherPaths {

    private val os = System.getProperty("os.name").lowercase()

    // CORRECCIÓN: os.contains() es más seguro que un "=" exacto,
    // ya que System.getProperty("os.name") suele devolver "Windows 11", "Mac OS X", etc.
    private val baseDir = when {
        os.contains("win") -> {
            System.getenv("APPDATA") ?: "${System.getProperty("user.home")}\\AppData\\Roaming"
        }

        os.contains("mac") -> {
            "${System.getProperty("user.home")}/Library/Application Support"
        }

        else -> {
            System.getenv("XDG_CONFIG_HOME") ?: "${System.getProperty("user.home")}/.config"
        }
    }

    val launcher = File(baseDir, ".ModdyLauncher")
    val launcherLogs = File(launcher, "logs")
    val meta = File(launcher, "meta")

    val assets = File(meta, "assets")
    val javaVersions = File(meta, "java_versions")
    val libraries = File(meta, "libraries")
    val logConfigs = File(meta, "log_configs")
    val natives = File(meta, "natives")
    val versions = File(meta, "versions")

    val profiles = File(launcher, "profiles")

    // Representa la estructura de un Perfil específico
    class ProfileDirectory(val root: File) {
        val dataPacks = File(root, "datapacks")
        val crashReports = File(root, "crash-reports")
        val mods = File(root, "mods")
        val resourcepacks = File(root, "resourcepacks")
        val saves = File(root, "saves")
        val shaderpacks = File(root, "shaderpacks")

        init {
            // Este bloque se ejecuta automáticamente al instanciar la clase.
            // Crea todas las carpetas del perfil si no existen físicamente.
            val allFolderStructure = listOf(root, dataPacks, crashReports, mods, resourcepacks, saves, shaderpacks)
            for (folder in allFolderStructure) {
                if (!folder.exists()) {
                    folder.mkdirs()
                }
            }
        }
    }

    /**
     * Inicializa y devuelve la estructura completa de un perfil.
     * Crea los directorios físicos en el disco inmediatamente.
     */
    fun newProfile(profileName: String): ProfileDirectory {
        val profileRoot = File(profiles, profileName)
        return ProfileDirectory(profileRoot)
    }

    /**
     * Opcional: Asegura que la estructura raíz del launcher (meta, assets, etc.) exista.
     * Puedes llamarlo al iniciar tu launcher.
     */
    fun initBaseStructure() {
        val baseFolders = listOf(
            launcher,
            launcherLogs,
            meta,
            assets,
            javaVersions,
            libraries,
            logConfigs,
            natives,
            versions,
            profiles
        )
        baseFolders.forEach { if (!it.exists()) it.mkdirs() }
    }
}