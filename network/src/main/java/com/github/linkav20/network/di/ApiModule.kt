package com.github.linkav20.network.di

import com.github.linkav20.network.data.api.AuthApi
import com.github.linkav20.network.data.api.EventApi
import com.github.linkav20.network.data.api.NotificationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun providesAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun providesEventApi(retrofit: Retrofit): EventApi =
        retrofit.create(EventApi::class.java)

    @Singleton
    @Provides
    fun providesNotificationApi(retrofit: Retrofit): NotificationApi =
        retrofit.create(NotificationApi::class.java)
}
