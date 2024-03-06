package com.github.linkav20.lists.di

import com.github.linkav20.lists.data.ListsRepositoryImpl
import com.github.linkav20.lists.domain.repository.ListsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ListsModule {

    @Binds
    @Singleton
    fun providesListsRepository(listsRepositoryImpl: ListsRepositoryImpl): ListsRepository
}
