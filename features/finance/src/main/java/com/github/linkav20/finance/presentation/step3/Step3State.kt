package com.github.linkav20.finance.presentation.step3

data class Step3State(
    val loading: Boolean = false,
    val isManager: Boolean = false,
    val sum: Double? = null,
    val dept: Double? = null,
    val myTotal: Double? = null,
    val isDept: Boolean = false,
    val showDialog: Boolean = false
)
