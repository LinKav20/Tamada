package com.github.linkav20.network.di

import com.github.linkav20.network.data.entity.Serializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val TIMEOUT_IN_SEC = 30L

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttp(
        interceptors: Set<@JvmSuppressWildcards Interceptor>,
        authenticators: Set<@JvmSuppressWildcards Authenticator>,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .dispatcher(Dispatcher().apply { maxRequestsPerHost = 20 })
            .also { builder -> interceptors.forEach { builder.addInterceptor(it) } }
            .also { builder -> authenticators.forEach { builder.authenticator(it) } }
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .build()

    @Singleton
    @Provides
    fun providesRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .callFactory(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(Serializer.moshi))

    @Singleton
    @Provides
    fun providesJson(): Json = Json { ignoreUnknownKeys = true }
}