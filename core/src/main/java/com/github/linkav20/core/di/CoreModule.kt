package com.github.linkav20.core.di

import com.github.linkav20.core.data.BottomNavigationRepositoryImpl
import com.github.linkav20.core.data.PartyIdRepositoryImpl
import com.github.linkav20.core.data.UserInformationRepositoryImpl
import com.github.linkav20.core.domain.repository.BottomNavigationRepository
import com.github.linkav20.core.domain.repository.PartyIdRepository
import com.github.linkav20.core.domain.repository.UserInformationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CoreModule {

    @Binds
    @Singleton
    fun providesPartyIdRepository(partyIdRepositoryImpl: PartyIdRepositoryImpl): PartyIdRepository

    @Binds
    @Singleton
    fun providesUserInformationRepository(userInformationRepositoryImpl: UserInformationRepositoryImpl): UserInformationRepository

    @Binds
    @Singleton
    fun providesBottomNavigationRepository(bottomNavigationRepositoryImpl: BottomNavigationRepositoryImpl): BottomNavigationRepository
}
