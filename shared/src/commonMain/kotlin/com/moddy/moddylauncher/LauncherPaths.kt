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

    /**
     * Carpeta del launcher
     */
    val launcher = File(baseDir, ".ModdyLauncher")

    /**
     * Carpeta de logs del launcher
     */
    val launcherLogs = File(launcher, "logs")

    /**
     * Carpeta de archivos para el juego
     */
    val meta = File(launcher, "meta")

    /**
     * Carpeta de los assets del juego
     */
    val assets = File(meta, "assets")

    /**
     * Carpeta donde se guardan los manifest de los assets
     */
    val index = File(launcher, "index")

    /**
     * Carpeta donde se guardan los assets de los assets
     */
    val objects = File(launcher, "objects")

    /**
     * Carpeta de versiones de java
     */
    val javaVersions = File(meta, "java_versions")

    /**
     * Carpeta de dependencias del juego
     */
    val libraries = File(meta, "libraries")

    /**
     * Carpeta de logs de configuracion
     */
    val logConfigs = File(meta, "log_configs")

    /**
     * Carpeta de dependencias nativas del juego
     */
    val natives = File(meta, "natives")

    /**
     * Carpeta de versiones del juego
     */
    val versions = File(meta, "versions")

    /**
     * Carpeta de perfiles creados
     */
    val profiles = File(launcher, "profiles")

    // Representa la estructura de un Perfil específico
    class ProfileDirectory(root: File) {
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