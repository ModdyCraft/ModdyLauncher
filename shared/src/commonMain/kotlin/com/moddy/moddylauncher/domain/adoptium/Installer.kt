package com.moddy.moddylauncher.domain.adoptium


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Installer(
    @SerialName("checksum")
    val checksum: String,
    @SerialName("checksum_link")
    val checksumLink: String,
    @SerialName("download_count")
    val downloadCount: Int,
    @SerialName("link")
    val link: String,
    @SerialName("metadata_link")
    val metadataLink: String,
    @SerialName("name")
    val name: String,
    @SerialName("signature_link")
    val signatureLink: String,
    @SerialName("size")
    val size: Int
)