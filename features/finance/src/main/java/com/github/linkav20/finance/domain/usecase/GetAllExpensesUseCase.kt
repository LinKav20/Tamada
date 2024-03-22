package com.github.linkav20.finance.domain.usecase

import android.content.Context
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.UserUI
import com.github.linkav20.finance.domain.repository.FinanceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetAllExpensesUseCase @Inject constructor(
    private val repository: FinanceRepository,
    @ApplicationContext private val context: Context
) {

    suspend fun invoke(id: Long) = repository.getAllExpanses(id).map {
        UserUI(
            id = it.id,
            name = it.name,
            expenses = it.expenses,
            focusSum = Expense(
                id = 1231314,
                name = context.getString(R.string.progress_user_total_sum),
                sum = it.expenses.sumOf { it.sum }
            ),
            isMe = it.expenses.sumOf { it.sum } > 1000
        )
    }
}