package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class GetTotalPartySumUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {

    suspend fun invoke(): Double? {
        val partyId = getPartyIdUseCase.invoke() ?: return null
        val totalSum = repository.getTotalSum(partyId = partyId)
        return totalSum?.toDouble()
    }
}
