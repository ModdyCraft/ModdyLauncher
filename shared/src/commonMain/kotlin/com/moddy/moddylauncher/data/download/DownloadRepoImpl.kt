package com.moddy.moddylauncher.data.download

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.io.File

class DownloadRepoImpl(
    private val client: HttpClient,
) : DownloadRepository {
    override suspend fun downloadFile(
        url: String,
        destination: File,
        output: (String) -> Unit
    ) {
        destination.parentFile?.mkdirs()

        if (destination.exists()) {
            output(
                "[DOWNLOADING] \"${destination.name}\" \"100%\" " +
                        "-> \"${formatBytes(destination.length())}\" " +
                        "Stored in \"${destination.parentFile?.absolutePath}\""
            )
            return
        }

        val tempFile = File(
            destination.parentFile,
            "${destination.name}.part"
        )

        output(
            "[DOWNLOADING] \"${destination.name}\" \"0%\" " +
                    "-> \"0 B\" " +
                    "Stored in \"${destination.parentFile?.absolutePath}\""
        )

        client.prepareGet(url).execute { response ->

            if (!response.status.isSuccess()) {
                error("Error descargando archivo (${response.status})")
            }

            val totalBytes = response.contentLength() ?: -1L
            var downloadedBytes = 0L

            tempFile.outputStream().buffered().use { fileOutput ->

                val channel = response.bodyAsChannel()
                val buffer = ByteArray(8192)

                while (!channel.isClosedForRead) {
                    val read = channel.readAvailable(buffer)

                    if (read == -1) {
                        break
                    }

                    fileOutput.write(buffer, 0, read)
                    downloadedBytes += read

                    val percentage =
                        if (totalBytes > 0) {
                            (downloadedBytes * 100 / totalBytes)
                        } else {
                            0
                        }

                    output(
                        "\r[DOWNLOADING] \"${destination.name}\" " +
                                "\"$percentage%\" -> " +
                                "\"${formatBytes(downloadedBytes)} / ${formatBytes(totalBytes)}\" " +
                                "Stored in \"${destination.parentFile?.absolutePath}\""
                    )
                }
            }
        }

        if (destination.exists()) {
            destination.delete()
        }

        if (!tempFile.renameTo(destination)) {
            error("No se pudo mover ${tempFile.absolutePath} a ${destination.absolutePath}")
        }

        output(
            "\r[DOWNLOADING] \"${destination.name}\" \"100%\" -> " +
                    "\"${formatBytes(destination.length())}\" " +
                    "Stored in \"${destination.parentFile?.absolutePath}\""
        )
    }

    override suspend fun downloadFilesInParallel(
        files: List<Pair<String, File>>,
        maxParallelDownloads: Int,
        output: (String) -> Unit
    ) = coroutineScope {
        val semaphore = Semaphore(maxParallelDownloads)

        files
            .distinctBy { it.second }
            .map { (url, destination) ->
                async {
                    semaphore.withPermit {
                        downloadFile(
                            url = url,
                            destination = destination,
                            output = output
                        )
                    }
                }
            }.awaitAll()

        println("Downloaded ${files.size} files")
    }

    private fun formatBytes(bytes: Long): String {
        if (bytes < 0) return "Unknown"

        return when {
            bytes >= 1024 * 1024 * 1024 ->
                "%.2f GB".format(bytes / (1024.0 * 1024.0 * 1024.0))

            bytes >= 1024 * 1024 ->
                "%.2f MB".format(bytes / (1024.0 * 1024.0))

            bytes >= 1024 ->
                "%.2f KB".format(bytes / 1024.0)

            else ->
                "$bytes B"
        }
    }
}