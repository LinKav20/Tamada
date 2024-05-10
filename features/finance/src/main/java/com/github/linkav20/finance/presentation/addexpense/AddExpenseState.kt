package com.github.linkav20.finance.presentation.addexpense

import android.net.Uri
import com.github.linkav20.finance.domain.model.Expense

data class AddExpenseState(
    val loading: Boolean = false,
    val id: Long? = null,
    val name: String = "",
    val sum: String = "",
    val receipt: Uri? = null,
    val action: Action? = null,
    val type: Expense.Type = Expense.Type.SUM,
    val actionType: ActionType = ActionType.ADD
) {
    enum class Action { BACK }

    enum class ActionType { UPDATE, ADD }
}
