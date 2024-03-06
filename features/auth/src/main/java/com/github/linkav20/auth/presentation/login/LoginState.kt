package com.github.linkav20.auth.presentation.login

data class LoginState(
    val action: Action? = null,
    val loading: Boolean = false,
    val login: String = "",
    val password: String = "",
    val isShowPassword: Boolean = false,
) {

    enum class Action { MAIN }
}
