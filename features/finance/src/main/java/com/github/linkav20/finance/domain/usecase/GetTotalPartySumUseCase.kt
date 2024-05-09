package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class GetTotalPartySumUseCase @Inject constructor(
    private val repository: FinanceRepository
) {

    suspend fun invoke(partyId: Long): Double? {
        val totalSum = repository.getTotalSum(partyId = partyId)
        return totalSum?.toDouble()
    }
}
