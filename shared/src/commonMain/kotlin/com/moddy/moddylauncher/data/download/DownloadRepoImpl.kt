package com.moddy.moddylauncher.data.download

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
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

        println("Existing file ${destination.path}")

        if (destination.exists()) return

        println("Downloading ${destination.path}")

        client.get(url)
            .bodyAsChannel()
            .copyTo(destination.outputStream())

        println("Downloaded ${destination.path}")
    }

    override suspend fun downloadFilesInParallel(
        files: List<Pair<String, File>>,
        maxParallelDownloads: Int
    ) = coroutineScope {
        val semaphore = Semaphore(maxParallelDownloads)

        files
            .distinctBy { it.first }
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
    }
}