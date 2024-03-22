package com.github.linkav20.finance.presentation.myexpenses

import android.net.Uri
import com.github.linkav20.finance.domain.model.Expense


data class MyExpensesState(
    val loading: Boolean = true,
    val showDialog: Boolean = false,
    val selectExpense: Expense? = null,
    val uri: Uri? = null,
    val expenses: List<Expense> = emptyList()
)