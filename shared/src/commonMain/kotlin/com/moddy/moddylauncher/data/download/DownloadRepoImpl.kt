package com.moddy.moddylauncher.data.download

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.io.File

class DownloadRepoImpl(
    private val client: HttpClient,
) : DownloadRepository {
    override suspend fun downloadFile(url: String, destination: File) {
        destination.parentFile?.mkdirs()

        println("Downloading $url")

        val tempFile = File(destination.parentFile, "${destination.name}.part")

        client.prepareGet(url).execute { response ->

            println(response.status)
            println(response.headers["Location"])
            println(response.headers["Content-Type"])
            println(response.headers["Content-Length"])

            if (!response.status.isSuccess()) {
                error("Error descargando archivo (${response.status})")
            }

            tempFile.outputStream().buffered().use { output ->
                response.bodyAsChannel().copyTo(output)
            }
        }

        if (destination.exists()) {
            destination.delete()
        }

        tempFile.renameTo(destination)
    }

    override suspend fun downloadFilesInParallel(
        files: List<Pair<String, File>>,
        maxParallelDownloads: Int
    ) = coroutineScope {
        val semaphore = Semaphore(maxParallelDownloads)

        files
            .distinctBy { it.second }
            .map { (url, destination) ->
                async {
                    semaphore.withPermit {
                        downloadFile(
                            url = url,
                            destination = destination
                        )
                    }
                }
            }.awaitAll()

        println("Downloaded ${files.size} files")
    }
}