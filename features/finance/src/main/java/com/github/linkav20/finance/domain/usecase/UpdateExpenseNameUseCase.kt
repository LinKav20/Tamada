package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class UpdateExpenseNameUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val userInformationRepository: UserInformationRepository
) {

    suspend fun invoke(id: Long, name: String) {
        val partyId = getPartyIdUseCase.invoke() ?: return
        val userId = userInformationRepository.userId
        repository.updateExpenseName(
            partyId = partyId,
            userId = userId.toLong(),
            expenseId = id,
            name = name
        )
    }
}
