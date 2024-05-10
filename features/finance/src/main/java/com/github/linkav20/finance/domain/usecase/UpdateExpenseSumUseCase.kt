package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class UpdateExpenseSumUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val userInformationRepository: UserInformationRepository
) {

    suspend fun invoke(id: Long, sum: Double) {
        val partyId = getPartyIdUseCase.invoke() ?: return
        val userId = userInformationRepository.userId
        repository.updateExpenseSum(
            partyId = partyId,
            userId = userId.toLong(),
            expenseId = id,
            sum = sum
        )
    }
}
