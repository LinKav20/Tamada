package com.github.linkav20.auth.presentation.signup

data class SignUpState(
    val action: Action? = null,
    val loading: Boolean = false,
    val login: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val showPassword: Boolean = false,
    val showRepeatedPassword: Boolean = false
) {
    enum class Action { MAIN }
}
