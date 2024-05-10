package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.repository.FinanceRepository
import java.time.OffsetDateTime
import javax.inject.Inject

class GetDeadlineUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {
    suspend fun invoke(): OffsetDateTime? {
        val partyId = getPartyIdUseCase.invoke() ?: return null
        return repository.getDeadline(partyId)
    }
}
