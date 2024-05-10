package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.model.Calculate
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class GetCalculateResultUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val partyIdUseCase: GetPartyIdUseCase
) {

    suspend fun invoke(id: Long): Calculate? {
        val partyId = partyIdUseCase.invoke() ?: return null
        return repository.calculateExpenses(
            partyId = partyId,
            userId = id
        )
    }
}
