package com.github.linkav20.finance.presentation.step1

import com.github.linkav20.finance.domain.model.Expense
import java.time.OffsetDateTime

data class Step1State(
    val loading: Boolean = true,
    val isManager: Boolean = false,
    val deadline: OffsetDateTime? = null,
    val canDeadlineEdit: Boolean = false,
    val canWalletEdit: Boolean = false,
    val sum: Double? = null,
    val expenses: List<Expense> = emptyList(),
    val cardNumber: String = "",
    val phoneNumber: String = "",
    val bank: String? = null,
    val cardOwner: String? = null,
    val showDialog: Boolean = false
) {
    val isDeadlineEditable: Boolean = canDeadlineEdit && isManager
    val isWalletEditable: Boolean = canWalletEdit && isManager
}
