package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.finance.domain.model.FinanceState
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class UpdateFinanceStateUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getUserInformationRepository: UserInformationRepository
) {

    suspend fun invoke(state: FinanceState, id: Long) {
        val userId = getUserInformationRepository.userId.toLong()
        repository.updateFinanceState(state = state, partyId = id, userId = userId)
    }
}
