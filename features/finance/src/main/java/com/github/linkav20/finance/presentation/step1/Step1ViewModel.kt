package com.github.linkav20.finance.presentation.step1

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.FinanceState
import com.github.linkav20.finance.domain.usecase.EndFinanceStepUseCase
import com.github.linkav20.finance.domain.usecase.GetMyExpenseUseCase
import com.github.linkav20.finance.domain.usecase.GetTotalPartySumUseCase
import com.github.linkav20.finance.domain.usecase.SaveWalletDataUseCase
import com.github.linkav20.finance.domain.usecase.UpdateFinanceStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class Step1ViewModel @Inject constructor(
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val getUserRoleUseCase: GetRoleUseCase,
    private val reactUseCase: ReactUseCase,
    private val getMyExpenseUseCase: GetMyExpenseUseCase,
    private val saveWalletDataUseCase: SaveWalletDataUseCase,
    private val endFinanceStepUseCase: EndFinanceStepUseCase,
    private val getTotalPartySumUseCase: GetTotalPartySumUseCase,
    @ApplicationContext
    private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(Step1State())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onRetry() = loadData()

    fun onEndStep() = viewModelScope.launch {
        try {
            val id = getPartyIdUseCase.invoke() ?: return@launch
            endFinanceStepUseCase.invoke(
                newState = FinanceState.STEP_2,
                partyId = id
            )
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        }
    }

    fun onDeadlineEditClick() = _state.update { it.copy(canDeadlineEdit = true) }

    fun onDeadlineChanged(value: OffsetDateTime) {
        if (value < OffsetDateTime.now()) {
            reactUseCase.invoke(
                title = context.getString(R.string.step1_deadline_error_title),
                style = ReactionStyle.ERROR
            )
        } else {
            _state.update { it.copy(deadline = value) }
            // go to server?
        }
    }

    fun onDeadlineSet() = _state.update { it.copy(canDeadlineEdit = false) }

    fun onPhoneNumberChange(value: String) = _state.update { it.copy(phoneNumber = value) }

    fun onShowDialog() = _state.update { it.copy(showDialog = true) }

    fun onCloseDialog() = _state.update { it.copy(showDialog = false) }

    fun onBankChange(value: String) = _state.update { it.copy(bank = value) }

    fun onCardOwnerChange(value: String) = _state.update { it.copy(cardOwner = value) }

    fun onCardNumberChange(value: String) = _state.update { it.copy(cardNumber = value) }

    fun onSaveWalletData() = viewModelScope.launch {
        if (state.value.phoneNumber.isEmpty() || state.value.cardNumber.isEmpty()) {
            reactUseCase.invoke(
                title = context.resources.getString(R.string.wallet_empty_error_popup),
                style = ReactionStyle.ERROR
            )
            return@launch
        }
        _state.update { it.copy(canWalletEdit = false, loading = true) }
        try {
            val id = getPartyIdUseCase.invoke() ?: return@launch
            saveWalletDataUseCase.invoke(
                id,
                cardNumber = state.value.cardNumber,
                phoneNumber = state.value.cardNumber
            )
        } catch (_: Exception) {

        }
        _state.update { it.copy(loading = false) }
    }

    fun onWalletEditClick() = _state.update { it.copy(canWalletEdit = true) }

    private fun loadData() = viewModelScope.launch {
        try {
            _state.update { it.copy(loading = true) }
            val id = getPartyIdUseCase.invoke() ?: return@launch
            val role = getUserRoleUseCase.invoke()
            val expenses = getMyExpenseUseCase.invoke(id)
            val total = getTotalPartySumUseCase.invoke(id)
            _state.update {
                it.copy(
                    loading = false,
                    isManager = role == UserRole.MANAGER,
                    expenses = expenses,
                    sum = total
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(error = e) }
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }
}
