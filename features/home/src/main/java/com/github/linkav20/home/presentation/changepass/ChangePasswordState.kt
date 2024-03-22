package com.github.linkav20.home.presentation.changepass

data class ChangePasswordState(
    val loading: Boolean = false,
    val currentPassword: String = "",
    val showCurrentPassword:Boolean=false,
    val newPassword: String = "",
    val showNewPassword:Boolean=false,
    val repeatedPassword: String = "",
    val showRepeatedPassword:Boolean=false,
    val action: Action? = null
) {
    enum class Action { BACK }
}
