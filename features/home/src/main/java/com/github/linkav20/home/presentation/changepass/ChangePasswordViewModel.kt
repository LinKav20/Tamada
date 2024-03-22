package com.github.linkav20.home.presentation.changepass

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.home.R
import com.github.linkav20.home.domain.usecase.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val reactUseCase: ReactUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(ChangePasswordState())
    val state = _state.asStateFlow()

    fun onPasswordChange(value: String) = _state.update { it.copy(currentPassword = value) }
    fun onRepeatedNewPasswordChange(value: String) =
        _state.update { it.copy(repeatedPassword = value) }

    fun onNewPasswordChange(value: String) = _state.update { it.copy(newPassword = value) }

    fun onPasswordShowChange() =
        _state.update { it.copy(showCurrentPassword = !state.value.showCurrentPassword) }

    fun onRepeatedNewPasswordShowChange() =
        _state.update { it.copy(showRepeatedPassword = !state.value.showRepeatedPassword) }

    fun onNewPasswordShowChange() =
        _state.update { it.copy(showNewPassword = !state.value.showNewPassword) }

    fun onPasswordUpdateClick() = viewModelScope.launch {
        if (state.value.newPassword == state.value.repeatedPassword) {
            _state.update { it.copy(loading = true) }
            try {
                changePasswordUseCase.invoke(
                    currentPassword = state.value.currentPassword,
                    newPassword = state.value.newPassword
                )
                delay(1000)
                reactUseCase.invoke(
                    title = context.getString(R.string.change_password_success_popup),
                    style = ReactionStyle.SUCCESS
                )
                _state.update { it.copy(action = ChangePasswordState.Action.BACK) }
            } catch (_: Exception) {
                reactUseCase.invoke(
                    title = context.getString(R.string.change_password_error_popup),
                    style = ReactionStyle.ERROR
                )
            }
            _state.update { it.copy(loading = false) }
        } else {
            reactUseCase.invoke(
                title = context.getString(R.string.change_password_password_not_the_same_error),
                style = ReactionStyle.ERROR
            )
        }
    }
}
