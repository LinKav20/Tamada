package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class GetDeadlineUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend fun invoke(partyId: Long) = repository.getDeadline(partyId)
}
