package com.github.linkav20.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoggingModule {

    @Provides
    @IntoSet
    fun providesConsoleLoggingInterceptor(interceptor: HttpLoggingInterceptor): Interceptor =
        Interceptor { chain ->
            val request: Request = chain.request()
            val isMultipart: Boolean = request.body is MultipartBody
            Timber.tag("NETWORK").d(request.url.toString())
            interceptor.setLevel(
                if (isMultipart) {
                    HttpLoggingInterceptor.Level.HEADERS
                } else {
                    HttpLoggingInterceptor.Level.BODY
                }
            )
            chain.proceed(request)
        }

    @Singleton
    @Provides
    fun providesConsoleHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(Timber::d)
}