package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class LoadFinanceStateUseCase @Inject constructor(
    private val repository: FinanceRepository
) {

    suspend fun invoke(id: Long) = repository.loadFinanceState(id)
}
