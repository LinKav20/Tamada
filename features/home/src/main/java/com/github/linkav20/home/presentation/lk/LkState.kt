package com.github.linkav20.home.presentation.lk

import com.github.linkav20.home.domain.model.User

data class LkState(
    val loading: Boolean = false,
    val user: User? = null,
    val isWalletEdit: Boolean = false,
    val isInfoEdit: Boolean = false,
    val action: Action? = null
) {
    enum class Action { AUTH }
}
