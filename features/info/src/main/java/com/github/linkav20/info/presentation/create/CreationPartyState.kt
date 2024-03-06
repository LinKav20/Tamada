package com.github.linkav20.info.presentation.create

import java.time.OffsetDateTime

data class CreationPartyState(
    val tab: Tab = Tab.MAIN,
    val loading: Boolean = false,
    val action: Action? = null,
    val name: String? = null,
    val startTime: OffsetDateTime? = null,
    val endTime: OffsetDateTime? = null,
    val address: String? = null,
    val addressAdditional: String? = null,
    val isExpenses: Boolean = true,
    val important: String? = null,
    val theme: String? = null,
    val dressCode: String? = null,
    val moodboadLink: String? = null,
) {
    enum class Tab(val index: Int) {
        MAIN(0),
        ADDRESS(1),
        IMPORTANT(2),
        THEME(3),
    }

    enum class Action { BACK }

    val isPreviousStep = tab != Tab.MAIN

    val tabsCount = Tab.values().size
}
