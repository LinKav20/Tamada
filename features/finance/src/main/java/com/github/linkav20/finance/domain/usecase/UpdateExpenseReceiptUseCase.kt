package com.github.linkav20.finance.domain.usecase

import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class UpdateExpenseReceiptUseCase @Inject constructor(
    private val repository: FinanceRepository
){

   // suspend fun invoke() = repository.updateExpenseReceipt()
}
