package com.github.linkav20.home.di

import com.github.linkav20.home.data.PartyInfoRepositoryImpl
import com.github.linkav20.home.domain.repository.PartyInfoRepository
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
}
