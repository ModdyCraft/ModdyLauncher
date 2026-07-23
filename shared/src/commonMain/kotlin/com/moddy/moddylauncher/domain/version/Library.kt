package com.moddy.moddylauncher.domain.version

data class Library(
    val downloads: DownloadsX,
    val name: String,
    val rules: List<RuleX>
)