package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class GetMyExpenseUseCase @Inject constructor(
    private val repository: FinanceRepository,
    private val userInformationRepository: UserInformationRepository
) {
    suspend fun invoke(id: Long) = repository.getExpenses(id).expenses
}
