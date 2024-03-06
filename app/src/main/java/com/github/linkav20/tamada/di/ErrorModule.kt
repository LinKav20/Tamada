package com.github.linkav20.tamada.di

import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.tamada.presentation.error.ErrorMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private interface ErrorModule {

    @Binds
    @Singleton
    fun provideErrorMapper(errorToComposableMapperImpl: ErrorMapperImpl): ErrorMapper
}
