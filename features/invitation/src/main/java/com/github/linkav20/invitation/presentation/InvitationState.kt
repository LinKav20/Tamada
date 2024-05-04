package com.github.linkav20.invitation.presentation

import com.github.linkav20.invitation.domain.model.Party

data class InvitationState(
    val loading: Boolean = true,
    val party: Party? = null,
    val exception: Exception? = null,
    val action: Action? = null
) {
    enum class Action {
        BACK
    }
}
