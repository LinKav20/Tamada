package com.github.linkav20.info.di

import com.github.linkav20.info.data.PartyRepositoryImpl
import com.github.linkav20.info.domain.repository.PartyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private interface InfoModule {
    @Binds
    @Singleton
    fun providesPartyInfoRepository(partyRepositoryImpl: PartyRepositoryImpl): PartyRepository
}
