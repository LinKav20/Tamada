package com.github.linkav20.notifications.di

import com.github.linkav20.notifications.data.NotificationRepositoryImpl
import com.github.linkav20.notifications.domain.repository.NotificationsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NotificationModule {


    @Binds
    @Singleton
    fun providesNotificationsRepository(impl: NotificationRepositoryImpl): NotificationsRepository

}
