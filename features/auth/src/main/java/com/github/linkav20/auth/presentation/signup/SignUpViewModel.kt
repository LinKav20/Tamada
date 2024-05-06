package com.github.linkav20.auth.presentation.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.auth.R
import com.github.linkav20.auth.domain.usecase.CreateUserUseCaseAndLogin
import com.github.linkav20.auth.domain.usecase.LogoutUseCase
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.notification.ReactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserUseCaseAndLogin: CreateUserUseCaseAndLogin,
    private val reactUseCase: ReactUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    fun onLoginUpdate(value: String) = _state.update { it.copy(login = value) }

    fun onEmailUpdate(value: String) = _state.update { it.copy(email = value) }

    fun onPasswordUpdate(value: String) = _state.update { it.copy(password = value) }

    fun onRepeatedPasswordUpdate(value: String) = _state.update { it.copy(repeatPassword = value) }

    fun onShowPasswordClick() = _state.update { it.copy(showPassword = !state.value.showPassword) }

    fun onShowRepeatedPasswordClick() =
        _state.update { it.copy(showRepeatedPassword = !state.value.showRepeatedPassword) }

    fun nullifyAction() = _state.update { it.copy(action = null) }

    fun onSignUpClick() = viewModelScope.launch {
        if (state.value.password != state.value.repeatPassword) {
            reactUseCase.invoke(
                title = context.getString(R.string.sign_up_passwords_error),
                style = ReactionStyle.ERROR
            )
            return@launch
        }
        _state.update { it.copy(loading = true) }
        try {
            createUserUseCaseAndLogin.invoke(
                login = state.value.login,
                email = state.value.email,
                password = state.value.password
            )
            _state.update { it.copy(action = SignUpState.Action.MAIN) }
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        }
        _state.update { it.copy(loading = false) }
    }
}
