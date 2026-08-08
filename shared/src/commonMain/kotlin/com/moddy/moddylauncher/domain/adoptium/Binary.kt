package com.moddy.moddylauncher.domain.adoptium


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Binary(
    @SerialName("architecture")
    val architecture: String,
    @SerialName("download_count")
    val downloadCount: Int,
    @SerialName("heap_size")
    val heapSize: String,
    @SerialName("image_type")
    val imageType: String,
    @SerialName("installer")
    val installer: Installer,
    @SerialName("jvm_impl")
    val jvmImpl: String,
    @SerialName("os")
    val os: String,
    @SerialName("package")
    val packageX: Package,
    @SerialName("project")
    val project: String,
    @SerialName("scm_ref")
    val scmRef: String,
    @SerialName("updated_at")
    val updatedAt: String
)