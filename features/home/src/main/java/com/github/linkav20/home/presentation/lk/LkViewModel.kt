package com.github.linkav20.home.presentation.lk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.home.domain.model.isNotEmpty
import com.github.linkav20.home.domain.usecase.DeleteUserUseCase
import com.github.linkav20.home.domain.usecase.GetUserInfoUseCase
import com.github.linkav20.home.domain.usecase.SaveUserUseCase
import com.github.linkav20.home.domain.usecase.UpdateUserAvatarUseCase
import com.github.linkav20.home.domain.usecase.UpdateUserInfoIsWalletUseCase
import com.github.linkav20.home.domain.usecase.UpdateWalletCardBankUseCase
import com.github.linkav20.home.domain.usecase.UpdateWalletCardNumberUseCase
import com.github.linkav20.home.domain.usecase.UpdateWalletCardOwnerUseCase
import com.github.linkav20.home.domain.usecase.UpdateWalletCardPhoneUseCase
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
    private val saveUserUseCase: SaveUserUseCase,
    private val updateWalletCardBankUseCase: UpdateWalletCardBankUseCase,
    private val updateWalletCardNumberUseCase: UpdateWalletCardNumberUseCase,
    private val updateWalletCardOwnerUseCase: UpdateWalletCardOwnerUseCase,
    private val updateWalletCardPhoneUseCase: UpdateWalletCardPhoneUseCase,
    private val updateUserInfoIsWalletUseCase: UpdateUserInfoIsWalletUseCase,
    private val reactUseCase: ReactUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LkState())
    val state = _state.asStateFlow()

    fun onStart() = loadData()

    fun onEditWalletClick() = _state.update { it.copy(isWalletEdit = true) }

    fun onRetry() {
        _state.update { it.copy(exception = null) }
        loadData()
    }

    fun onEditInfoClick() = _state.update { it.copy(isInfoEdit = true) }

    fun onSaveWalletClick() {
        _state.update { it.copy(isWalletEdit = false) }
        saveWallet()
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

    fun onShowPasswordClick() = _state.update { it.copy(showPassword = !state.value.showPassword) }

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
                deleteUserUseCase.invoke(
                    user = user,
                    password = state.value.password
                )
            }
            onLogoutClick()
        } catch (_: Exception) {

        }
        _state.update { it.copy(loading = false) }
    }

    fun onShowDialog() = _state.update { it.copy(showDialog = true) }

    fun onCloseDialog() = _state.update { it.copy(showDialog = false) }

    fun onPasswordChanged(value: String) = _state.update { it.copy(password = value) }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val user = getUserInfoUseCase.invoke()
            _state.update { it.copy(user = user, wallet = user.wallet) }
        } catch (e: Exception) {
            _state.update { it.copy(exception = e) }
        }
        _state.update { it.copy(loading = false) }
    }

    fun onLogoutClick() {
        _state.update { it.copy(action = LkState.Action.AUTH) }
        // todo clear tokens
    }

    private fun saveUser() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val user = state.value.user
            if (user != null) {
                saveUserUseCase.invoke(state.value.user!!)
            }
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }

    private fun saveWallet() {
        val wallet = state.value.wallet
        if (wallet?.cardBank != state.value.user?.wallet?.cardBank) saveCardBank()
        if (wallet?.cardNumber != state.value.user?.wallet?.cardNumber) saveCardNumber()
        if (wallet?.cardOwner != state.value.user?.wallet?.cardOwner) saveCardOwner()
        if (wallet?.cardPhoneNumber != state.value.user?.wallet?.cardPhoneNumber) saveCardPhone()

        updateUserInfoIsWalletUseCase.invoke(state.value.user?.wallet?.isNotEmpty() == true)
    }

    private fun saveCardBank() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val bank = state.value.user?.wallet?.cardBank ?: return@launch
            updateWalletCardBankUseCase.invoke(bank)
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }

    private fun saveCardOwner() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val owner = state.value.user?.wallet?.cardOwner ?: return@launch
            updateWalletCardOwnerUseCase.invoke(owner)
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }

    private fun saveCardNumber() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val cardNumber = state.value.user?.wallet?.cardNumber ?: return@launch
            updateWalletCardNumberUseCase.invoke(cardNumber)
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }

    private fun saveCardPhone() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val cardPhoneNumber = state.value.user?.wallet?.cardPhoneNumber ?: return@launch
            updateWalletCardPhoneUseCase.invoke(cardPhoneNumber)
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }

}