package com.github.linkav20.network.di

import com.github.linkav20.network.utils.RetrofitErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton

private const val baseUrl = "http://158.160.59.43:8080/"

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun providesRetrofitErrorHandler(retrofit: Retrofit, json: Json) =
        RetrofitErrorHandler(retrofit, json)

    @Singleton
    @Provides
    fun providesRetrofit(retrofitBuilder: Retrofit.Builder): Retrofit =
        retrofitBuilder.baseUrl(baseUrl).build()
}
