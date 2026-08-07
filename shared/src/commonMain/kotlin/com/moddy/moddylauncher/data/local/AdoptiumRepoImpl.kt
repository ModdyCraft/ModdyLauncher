package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.LauncherPaths
import com.moddy.moddylauncher.data.download.DownloadRepository
import com.moddy.moddylauncher.data.remote.AdoptiumApi
import java.io.File
import java.util.zip.ZipInputStream

class AdoptiumRepoImpl(
    private val api: AdoptiumApi,
    private val downloader: DownloadRepository
) : AdoptiumRepo {

    override suspend fun downloadAdoptium(version: String) {

        val os = getAdoptiumOS()
        val arch = getAdoptiumArch()

        val asset = api.getAssetsById(
            id = version,
            arch = arch,
            os = os
        )

        val packageInfo = asset?.binary?.packageX

        val zipFile = File(
            LauncherPaths.javaJRE,
            packageInfo?.name.toString()
        )

        val destination = File(
            LauncherPaths.javaJRE,
            "$version-jre"
        )

        if (destination.exists()) {
            return
        }

        downloader.downloadFile(
            packageInfo?.link.toString(),
            zipFile
        )

        try {
            extractZip(
                zipFile,
                destination.parentFile
            )
        } finally {
            zipFile.delete()
        }
    }


    private fun extractZip(
        zipFile: File,
        destination: File
    ) {
        val root = destination.canonicalFile

        ZipInputStream(
            zipFile.inputStream().buffered()
        ).use { zip ->

            while (true) {
                val entry = zip.nextEntry ?: break

                val file = File(
                    root,
                    entry.name
                ).canonicalFile

                require(
                    file.path.startsWith(
                        root.path + File.separator
                    )
                ) {
                    "ZIP inválido: ${entry.name}"
                }

                if (entry.isDirectory) {
                    file.mkdirs()
                } else {
                    file.parentFile?.mkdirs()

                    file.outputStream().buffered().use {
                        zip.copyTo(it)
                    }
                }

                zip.closeEntry()
            }
        }
    }


    private fun getAdoptiumOS(): String =
        when {
            LauncherPaths.os.contains("Windows", true) -> "windows"
            LauncherPaths.os.contains("Linux", true) -> "linux"
            LauncherPaths.os.contains("Mac", true) -> "mac"
            else -> error("OS no soportado")
        }


    private fun getAdoptiumArch(): String =
        when (System.getProperty("os.arch").lowercase()) {
            "amd64", "x86_64" -> "x64"
            "x86", "i386", "i686" -> "x86"
            "aarch64", "arm64" -> "aarch64"
            "arm" -> "arm"
            else -> error("Arquitectura no soportada")
        }
}