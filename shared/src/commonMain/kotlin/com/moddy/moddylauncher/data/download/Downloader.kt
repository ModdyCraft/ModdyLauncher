package com.moddy.moddylauncher.data.download

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.io.File

suspend fun downloadFile(
    client: HttpClient,
    url: String,
    destination: File
) {
    val response = client.get(url)

    destination.outputStream().use { output ->
        response.bodyAsChannel().copyTo(output)
    }
}

suspend fun downloadFilesInParallel(
    client: HttpClient,
    files: List<Pair<String, File>>,
    maxParallelDownloads: Int = 3
) = coroutineScope {

    val semaphore = Semaphore(maxParallelDownloads)

    files
        .distinctBy { it.first }
        .map { (url, destination) ->
            async(Dispatchers.IO) {
                semaphore.withPermit {
                    downloadFile(
                        client = client,
                        url = url,
                        destination = destination
                    )
                }
            }
        }
        .awaitAll()
}