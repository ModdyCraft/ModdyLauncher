package com.moddy.moddylauncher.di

import com.moddy.moddylauncher.data.download.DownloadRepoImpl
import com.moddy.moddylauncher.data.download.DownloadRepository
import com.moddy.moddylauncher.data.local.MinecraftLauncher
import com.moddy.moddylauncher.data.local.MinecraftLauncherImpl
import com.moddy.moddylauncher.data.local.MinecraftRepoImpl
import com.moddy.moddylauncher.data.local.MinecraftRepository
import com.moddy.moddylauncher.data.remote.MinecraftApi
import com.moddy.moddylauncher.data.remote.MinecraftApiImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val AppModule = module {

    single {
        HttpClient(CIO) {

            install(HttpCache)
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 60_000
                connectTimeoutMillis = 30_000
                socketTimeoutMillis = 60_000
            }

            install(HttpRequestRetry) {
                maxRetries = 3

                retryIf { _, response ->
                    response.status.value in 500..599
                }

                retryOnExceptionIf { _, cause ->
                    cause is IOException
                }

                exponentialDelay()
            }
        }
    }

    single<MinecraftApi> { MinecraftApiImpl(get()) }

    single<MinecraftRepository> { MinecraftRepoImpl(get(), get(), get(), get()) }

    singleOf(::MinecraftLauncherImpl) bind MinecraftLauncher::class

    single<DownloadRepository> { DownloadRepoImpl(get()) }
}