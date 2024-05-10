package com.github.linkav20.finance.presentation.progress

import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.UserUI

data class ProgressState(
    val step: Int = 0,
    val loading: Boolean = true,
    val isManager: Boolean = false,
    val users: List<UserUI> = emptyList(),
    val total: Expense? = null
) {
    val getUsersDone: List<UserUI> = users.filter { it.focusSum.sum > 0.0 }

    val getUsersNotDone: List<UserUI> = users.filter { it.focusSum.sum <= 0.0 }
}
