package com.moddy.moddylauncher.di

import com.moddy.moddylauncher.data.download.DownloadRepoImpl
import com.moddy.moddylauncher.data.download.DownloadRepository
import com.moddy.moddylauncher.data.local.MinecraftRepoImpl
import com.moddy.moddylauncher.data.local.MinecraftRepository
import com.moddy.moddylauncher.data.remote.MinecraftApi
import com.moddy.moddylauncher.data.remote.MinecraftApiImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val AppModule = module {

    single {
        HttpClient(CIO) {

            install(HttpCache)
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    single<MinecraftApi> { MinecraftApiImpl(get()) }

    single<MinecraftRepository> { MinecraftRepoImpl(get(), get(), get()) }

    single<DownloadRepository> { DownloadRepoImpl(get()) }
}