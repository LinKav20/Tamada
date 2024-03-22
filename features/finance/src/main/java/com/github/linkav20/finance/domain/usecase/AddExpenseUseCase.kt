package com.github.linkav20.finance.domain.usecase

import android.net.Uri
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.repository.FinanceRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: FinanceRepository
) {

    suspend fun invoke(
        type: Expense.Type,
        name: String,
        sum: Double,
        receipt: Uri
    ) = repository.sendExpense(type = type, name = name, sum = sum, receipt = receipt)
}
