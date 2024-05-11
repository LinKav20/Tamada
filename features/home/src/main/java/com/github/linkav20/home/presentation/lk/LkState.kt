package com.github.linkav20.home.presentation.lk

import com.github.linkav20.home.domain.model.User
import com.github.linkav20.home.domain.model.Wallet

data class LkState(
    val loading: Boolean = false,
    val user: User? = null,
    val isWalletEdit: Boolean = false,
    val isInfoEdit: Boolean = false,
    val action: Action? = null,
    val exception: Exception? = null,
    val wallet: Wallet? = null,
    val showDialog: Boolean = false,
    val password: String = "",
    val showPassword: Boolean = false
) {
    enum class Action { AUTH }
}
