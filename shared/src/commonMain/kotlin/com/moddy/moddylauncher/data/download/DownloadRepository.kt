package com.moddy.moddylauncher.data.download

import java.io.File

interface DownloadRepository {

    suspend fun download(url: String, destination: File)

    suspend fun downloadFilesInParallel(
        parent: File,
        files: List<Pair<String, File>>,
        maxParallelDownloads: Int = 3
    ): List<Unit>
}