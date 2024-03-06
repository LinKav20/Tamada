package com.github.linkav20.home.presentation.main

import com.github.linkav20.home.domain.model.Party

data class HomeMainScreenState(
    val loading: Boolean = true,
    val parties: List<Party> = emptyList(),
    val action: Action? = null,
) {

    enum class Action {
        PARTY, AUTH
    }

    val guestParties = parties.filterNot { it.isManager }

    val managerParties = parties.filter { it.isManager }
}
