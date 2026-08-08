package com.moddy.moddylauncher.data.download

import java.io.File

interface DownloadRepository {

    suspend fun downloadFile(url: String, destination: File, output: (String) -> Unit)

    suspend fun downloadFilesInParallel(
        files: List<Pair<String, File>>,
        maxParallelDownloads: Int = 3,
        output: (String) -> Unit = {}
    )
}