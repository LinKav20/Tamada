package com.github.linkav20.auth.di

import com.github.linkav20.auth.data.AuthRepositoryImpl
import com.github.linkav20.auth.data.TokenRepositoryImpl
import com.github.linkav20.auth.domain.repository.AuthRepository
import com.github.linkav20.auth.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideTokenRepository(impl: TokenRepositoryImpl): TokenRepository
}
