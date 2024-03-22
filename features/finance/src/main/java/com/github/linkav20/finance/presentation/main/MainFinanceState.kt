package com.github.linkav20.finance.presentation.main

data class MainFinanceState(
    val isManager: Boolean = false,
    val showDialog: Boolean = false,
    val loading: Boolean = true,
    val throwable: Throwable? = null,
    val tab: Tab? = null
) {
    enum class Tab(val index: Int) {
        STEP1(0),
        STEP2(1),
        STEP3(2)
    }
}
