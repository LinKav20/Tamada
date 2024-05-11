package com.github.linkav20.finance.presentation.progress

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.DomainException
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.usecase.GetPartyNameUseCase
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.UserUI
import com.github.linkav20.finance.domain.usecase.GetAllUserReceipts
import com.github.linkav20.finance.domain.usecase.GetAllUsersWithExpenses
import com.github.linkav20.finance.domain.usecase.GetTotalPartySumUseCase
import com.github.linkav20.finance.navigation.ProgressDestination
import com.github.linkav20.notifications.domain.usecase.NotifyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val getTotalPartySumUseCase: GetTotalPartySumUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    private val getAllUsersWithExpenses: GetAllUsersWithExpenses,
    private val savedStateHandle: SavedStateHandle,
    private val getAllUserReceipts: GetAllUserReceipts,
    private val reactUseCase: ReactUseCase,
    private val getPartyNameUseCase: GetPartyNameUseCase,
    private val notifyUseCase: NotifyUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(ProgressState())
    val state = _state.asStateFlow()

    init {
        val step = ProgressDestination.extractStep(savedStateHandle)
        _state.update { it.copy(step = step) }
        loadData()
    }

    fun onRetry() = loadData()

    fun onExpandUserExpenseClick(userUI: UserUI) {
        val newUser = userUI.copy(isExpanded = !userUI.isExpanded)
        val index = _state.value.users.indexOf(userUI)
        val newList = _state.value.users.minus(userUI).toMutableList()
        newList.add(index, newUser)
        _state.update { it.copy(users = newList) }
    }

    fun onNullifyReceipt() = _state.update { it.copy(receipt = null) }

    fun onReceiptClick(userId: Long) = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val receipt =
                getAllUserReceipts.invoke(userId) ?: throw DomainException.Network
            _state.update { it.copy(receipt = receipt) }
        } catch (_: Exception) {
            reactUseCase.invoke(
                context.getString(R.string.my_expense_popup_receipt_not_loaded),
                style = ReactionStyle.ERROR
            )
        }
        _state.update { it.copy(loading = false) }
    }

    fun onErrorInExpensesClick(userId: Long) = viewModelScope.launch {
        try {
            val name = getPartyNameUseCase.invoke()
            notifyUseCase.invoke(
                toUserId = userId,
                title = context.getString(R.string.progress_error_in_expenses_title, name),
                subtitle = context.getString(R.string.progress_error_in_expenses_subtitle)
            )
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        }
    }

    private fun loadData() = viewModelScope.launch {
        try {
            _state.update { it.copy(loading = true) }
            val role = getRoleUseCase.invoke()
            val users = getAllUsersWithExpenses.invoke(state.value.step)
            val total = getTotalPartySumUseCase.invoke()
            _state.update {
                it.copy(
                    isManager = role == UserRole.MANAGER,
                    loading = false,
                    users = users,
                    total = Expense(
                        name = context.getString(R.string.progress_total_sum_of_party),
                        sum = total ?: 0.0
                    )
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(error = e) }
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }
}
