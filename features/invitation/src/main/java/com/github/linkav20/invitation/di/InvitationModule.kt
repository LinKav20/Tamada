package com.github.linkav20.invitation.di

import com.github.linkav20.invitation.data.PartyInvitationRepositoryImpl
import com.github.linkav20.invitation.domain.repository.PartyInvitationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private interface InvitationModule {

    @Binds
    @Singleton
    fun providesInvitationRepository(impl: PartyInvitationRepositoryImpl): PartyInvitationRepository
}
