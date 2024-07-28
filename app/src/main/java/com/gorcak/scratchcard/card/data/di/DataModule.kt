package com.gorcak.scratchcard.card.data.di

import com.gorcak.scratchcard.core.BASE_URL
import com.gorcak.scratchcard.card.data.MemoryStorage
import com.gorcak.scratchcard.card.data.RepositoryImpl
import com.gorcak.scratchcard.card.domain.ActivationValidator
import com.gorcak.scratchcard.card.domain.Repository
import com.gorcak.scratchcard.card.domain.Storage
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
}


private fun provideConverterFactory(): MoshiConverterFactory =
    MoshiConverterFactory.create()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    converterFactory: MoshiConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()
}

fun provideService(retrofit: Retrofit): com.gorcak.scratchcard.card.data.ApiService =
    retrofit.create(com.gorcak.scratchcard.card.data.ApiService::class.java)

val dataModule = module {
    singleOf(::MemoryStorage).bind<Storage>()
    singleOf(::RepositoryImpl).bind<Repository>()
    singleOf(::ActivationValidator)
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }
    single { provideService(get()) }
}