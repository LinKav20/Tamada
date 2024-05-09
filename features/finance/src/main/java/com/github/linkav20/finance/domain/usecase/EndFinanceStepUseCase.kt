package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.finance.domain.model.FinanceState
import javax.inject.Inject

class EndFinanceStepUseCase @Inject constructor(
    private val updateDeadlineUseCase: UpdateDeadlineUseCase,
    private val updateFinanceStateUseCase: UpdateFinanceStateUseCase
) {
    suspend fun invoke(newState: FinanceState, partyId: Long) {
        updateDeadlineUseCase.invoke(partyId = partyId)
        updateFinanceStateUseCase.invoke(state = newState, id = partyId)
    }
}
