package com.github.linkav20.finance.presentation.myexpenses

import com.github.linkav20.finance.domain.model.Expense
import java.io.InputStream


data class MyExpensesState(
    val loading: Boolean = true,
    val showDialog: Boolean = false,
    val selectExpense: Expense? = null,
    val receipt: InputStream? = null,
    val expenses: List<Expense> = emptyList(),
    val step: Int = 0
)