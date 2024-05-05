package com.github.linkav20.home.presentation.lk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.home.domain.usecase.DeleteUserUseCase
import com.github.linkav20.home.domain.usecase.GetUserInfoUseCase
import com.github.linkav20.home.domain.usecase.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LkViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LkState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onEditWalletClick() = _state.update { it.copy(isWalletEdit = true) }

    fun onRetry() {
        _state.update { it.copy(exception = null) }
        loadData()
    }

    fun onEditInfoClick() = _state.update { it.copy(isInfoEdit = true) }

    fun onSaveWalletClick() {

        _state.update { it.copy(isWalletEdit = false) }
    }

    fun onSaveInfoClick() {
        _state.update { it.copy(isInfoEdit = false) }
        saveUser()
    }

    fun onLoginChanged(value: String) {
        val user = state.value.user
        if (user != null) {
            val newUser = user.copy(login = value)
            _state.update { it.copy(user = newUser) }
        }
    }

    fun onEmailChanged(value: String) {
        val user = state.value.user
        if (user != null) {
            val newUser = user.copy(email = value)
            _state.update { it.copy(user = newUser) }
        }
    }

    fun onCardNumberChanged(value: String) {
        val user = state.value.user
        if (user != null) {
            val newWallet = user.wallet?.copy(cardNumber = value)
            val newUser = user.copy(wallet = newWallet)
            _state.update { it.copy(user = newUser) }
        }
    }

    fun onCardPhoneNumberChanged(value: String) {
        val user = state.value.user
        if (user != null) {
            val newWallet = user.wallet?.copy(cardPhoneNumber = value)
            val newUser = user.copy(wallet = newWallet)
            _state.update { it.copy(user = newUser) }
        }
    }

    fun onCardOwnerChanged(value: String) {
        val user = state.value.user
        if (user != null) {
            val newWallet = user.wallet?.copy(cardOwner = value)
            val newUser = user.copy(wallet = newWallet)
            _state.update { it.copy(user = newUser) }
        }
    }

    fun onCardBankChanged(value: String) {
        val user = state.value.user
        if (user != null) {
            val newWallet = user.wallet?.copy(cardBank = value)
            val newUser = user.copy(wallet = newWallet)
            _state.update { it.copy(user = newUser) }
        }
    }

    fun onDeleteAccount() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val user = state.value.user
            if (user != null) {
                deleteUserUseCase.invoke(state.value.user!!)
            }
            onLogoutClick()
        } catch (_: Exception) {

        }
        _state.update { it.copy(loading = false) }
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val user = getUserInfoUseCase.invoke()
            _state.update { it.copy(user = user) }
        } catch (e: Exception) {
            _state.update { it.copy(exception = e) }
        }
        _state.update { it.copy(loading = false) }
    }

    fun onLogoutClick() {
        _state.update { it.copy(action = LkState.Action.AUTH) }
        //todo clear tokens
    }

    private fun saveUser() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val user = state.value.user
            if (user != null) {
                saveUserUseCase.invoke(state.value.user!!)
            }
        } catch (_: Exception) {

        }
        _state.update { it.copy(loading = false) }
    }

}