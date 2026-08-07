package com.moddy.moddylauncher.data.local

import com.moddy.moddylauncher.LauncherPaths
import com.moddy.moddylauncher.data.download.DownloadRepository
import java.io.File
import java.util.zip.ZipInputStream

class AdoptiumRepoImpl(
    private val downloader: DownloadRepository
) : AdoptiumRepo {

    override suspend fun downloadAdoptium(version: String) {
        val os = LauncherPaths.os
            .trimStart()
            .substringBefore(" ")

        val arch = getAdoptiumArch()

        val zipFile = File(LauncherPaths.javaJRE, "$version.jre.zip")
        val destination = File(LauncherPaths.javaJRE, "$version-jre")

        if (destination.exists()) {
            return
        }

        destination.mkdirs()

        downloader.downloadFile(
            "https://api.adoptium.net/v3/binary/latest/$version/ga/$os/$arch/jre/hotspot/normal/eclipse",
            zipFile
        )

        try {
            extractZip(zipFile, destination)
        } finally {
        }
    }

    private fun extractZip(zipFile: File, destination: File) {
        val destinationRoot = destination.canonicalFile

        ZipInputStream(zipFile.inputStream().buffered()).use { zipInput ->
            var entry = zipInput.nextEntry

            while (entry != null) {
                val outputFile = File(destinationRoot, entry.name).canonicalFile

                require(outputFile.path.startsWith(destinationRoot.path + File.separator)) {
                    "Entrada ZIP inválida: ${entry.name}"
                }

                if (entry.isDirectory) {
                    outputFile.mkdirs()
                } else {
                    outputFile.parentFile?.mkdirs()

                    outputFile.outputStream().use {
                        zipInput.copyTo(it)
                    }
                }

                zipInput.closeEntry()
                entry = zipInput.nextEntry
            }
        }
    }

    fun getAdoptiumArch(): String =
        when (System.getProperty("os.arch").lowercase()) {
            "amd64", "x86_64" -> "x64"

            "x86", "i386", "i486", "i586", "i686" -> "x86"

            "aarch64", "arm64" -> "aarch64"

            "arm" -> "arm"

            "ppc64" -> "ppc64"
            "ppc64le" -> "ppc64le"

            "s390x" -> "s390x"

            "sparcv9" -> "sparcv9"

            "riscv64" -> "riscv64"

            else -> error("Arquitectura no soportada: ${System.getProperty("os.arch")}")
        }
}