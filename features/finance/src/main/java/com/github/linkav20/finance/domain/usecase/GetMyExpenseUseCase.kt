package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class GetMyExpenseUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val userInformationRepository: UserInformationRepository
) {
    suspend fun invoke(): List<Expense> {
        val myId = userInformationRepository.userId
        val partyId = getPartyIdUseCase.invoke() ?: return emptyList()
        return repository.getExpenses(
            partyId = partyId,
            userId = myId.toLong()
        )
    }
}
