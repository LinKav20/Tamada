package com.github.linkav20.finance.di

import com.github.linkav20.finance.data.FinanceRepositoryImpl
import com.github.linkav20.finance.domain.repository.FinanceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FinanceModule {

    @Binds
    @Singleton
    fun providesFinanceRepository(impl: FinanceRepositoryImpl): FinanceRepository
}
