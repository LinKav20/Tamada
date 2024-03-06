package com.github.linkav20.guests.di

import com.github.linkav20.guests.data.UsersRepositoryImpl
import com.github.linkav20.guests.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface GuestsModule {

    @Binds
    @Singleton
    fun providesUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository
}
