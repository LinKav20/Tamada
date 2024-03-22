package com.github.linkav20.home.di

import com.github.linkav20.auth.domain.usecase.GetRoleUseCaseImpl
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import com.github.linkav20.home.data.PartyInfoRepositoryImpl
import com.github.linkav20.home.data.UserRepositoryImpl
import com.github.linkav20.home.domain.repository.PartyInfoRepository
import com.github.linkav20.home.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private interface HomeModule {
    @Binds
    @Singleton
    fun providesPartyInfoRepository(partyInfoRepositoryImpl: PartyInfoRepositoryImpl): PartyInfoRepository

    @Binds
    @Singleton
    fun providesUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun providesGetRoleUseCase(getRoleUseCaseImpl: GetRoleUseCaseImpl): GetRoleUseCase
}
