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
    override suspend fun download(url: String, destination: File) {
        val response = client.get(url)

        destination.outputStream().use { output ->
            response.bodyAsChannel().copyTo(output)
        }
    }

    override suspend fun downloadFilesInParallel(
        parent: File,
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
                            client = client,
                            url = url,
                            destination = destination
                        )
                    }
                }
            }.awaitAll()
    }
}