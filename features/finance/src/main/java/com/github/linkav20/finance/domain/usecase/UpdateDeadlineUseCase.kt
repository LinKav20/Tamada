package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.finance.domain.repository.FinanceRepository
import java.time.OffsetDateTime
import javax.inject.Inject

class UpdateDeadlineUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend fun invoke(partyId: Long, deadline: OffsetDateTime? = null) =
        repository.updateDeadline(
            partyId = partyId,
            deadline = deadline
        )
}
