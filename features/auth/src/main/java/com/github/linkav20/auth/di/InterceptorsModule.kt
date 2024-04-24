package com.github.linkav20.auth.di

import com.github.linkav20.auth.interceptors.BearerAuthenticator
import com.github.linkav20.auth.interceptors.BearerInterceptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Authenticator
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
private interface InterceptorsModule {

    @Binds
    @IntoSet
    fun providesBearerInterceptor(bearerInterceptor: BearerInterceptor): Interceptor

    @Binds
    @IntoSet
    fun providesBearerAuthenticator(bearerAuthenticator: BearerAuthenticator): Authenticator
}
