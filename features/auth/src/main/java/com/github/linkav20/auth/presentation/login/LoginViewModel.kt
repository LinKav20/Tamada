package com.github.linkav20.auth.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.auth.R
import com.github.linkav20.auth.domain.usecase.LoginUseCase
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.notification.ReactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val reactUseCase: ReactUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onLoginUpdate(value: String) = _state.update { it.copy(login = value) }

    fun onPasswordUpdate(value: String) = _state.update { it.copy(password = value) }

    fun onShowPasswordClick() = _state.update { it.copy(isShowPassword = !state.value.isShowPassword) }

    fun nullifyAction() = _state.update { it.copy(action = null) }

    fun onLoginClick() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            delay(1000)
            loginUseCase.invoke(
                login = state.value.login,
                password = state.value.password
            )
            _state.update { it.copy(action = LoginState.Action.MAIN) }
        } catch (_: Exception) {
            reactUseCase.invoke(
                title = context.getString(R.string.login_screen_error_title),
                style = ReactionStyle.ERROR
            )
        }
        _state.update { it.copy(loading = false) }
    }
}