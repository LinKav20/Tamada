package com.github.linkav20.finance.presentation.step2

import com.github.linkav20.finance.domain.model.Calculate
import java.time.OffsetDateTime

data class Step2State(
    val loading: Boolean = true,
    val isManager: Boolean = false,
    val deadline: OffsetDateTime? = null,
    val canDeadlineEdit: Boolean = false,
    val canWalletEdit: Boolean = false,
    val sum: Double? = null,
    val dept: Double? = null,
    val myTotal: Double? = null,
    val cardNumber: String = "",
    val phoneNumber: String = "",
    val bank: String? = null,
    val cardOwner: String? = null,
    val isDept: Boolean = false,
    val showDialog: Boolean = false,
    val calculation: Calculate? = null,
    val error: Throwable? = null
) {
    val isDeadlineEditable: Boolean = canDeadlineEdit && isManager
    val isWalletEditable: Boolean = canWalletEdit && isManager
}
