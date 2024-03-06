package com.github.linkav20.finance.presentation.main

sealed class MainFinanceState {
    data class BasicState(
        val isManager: Boolean = false,
        val showDialog: Boolean = false
    ) : MainFinanceState()

    object Loading : MainFinanceState()

    data class Error(
        val throwable: Throwable
    ) : MainFinanceState()
}