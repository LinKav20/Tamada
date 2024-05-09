package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class GetUserExpensesUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val partyIdUseCase: GetPartyIdUseCase
) {
    suspend fun invoke(id: Long): List<Expense> {
        val partyId = partyIdUseCase.invoke() ?: return emptyList()
        return repository.getExpenses(
            partyId = partyId,
            userId = id
        )
    }
}
