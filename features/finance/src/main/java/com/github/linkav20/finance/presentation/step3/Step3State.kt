package com.github.linkav20.finance.presentation.step3

import com.github.linkav20.finance.domain.model.Calculate

data class Step3State(
    val loading: Boolean = false,
    val isManager: Boolean = false,
    val sum: Double? = null,
    val dept: Double? = null,
    val myTotal: Double? = null,
    val isDept: Boolean = false,
    val showDialog: Boolean = false,
    val error: Throwable? = null,
    val calculation: Calculate? = null
)
