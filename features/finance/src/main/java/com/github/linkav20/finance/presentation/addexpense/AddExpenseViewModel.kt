package com.github.linkav20.finance.presentation.addexpense

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.usecase.AddExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val reactUseCase: ReactUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(AddExpenseState())
    val state = _state.asStateFlow()

    fun initState(expense: Expense?) {
        if (expense != null) {
            _state.update {
                it.copy(
                    id = expense.id,
                    name = expense.name,
                    sum = expense.sum.toString(),
                    receipt = expense.uri
                )
            }
        }
    }

    fun initExpenseType(value: Expense.Type) {
        _state.update { it.copy(type = value) }
    }

    fun clearState() = _state.update { AddExpenseState() }

    fun onNameChanged(value: String) = _state.update { it.copy(name = value) }

    fun onSumChanged(value: String) = _state.update { it.copy(sum = value) }

    fun onReceiptChange(value: Uri?) = _state.update { it.copy(receipt = value) }

    fun onAddClick() {
        if (state.value.sum.isEmpty() || state.value.name.isEmpty() || state.value.receipt == null) {
            reactUseCase.invoke(
                title = context.getString(R.string.add_expense_popup_unfilled),
                style = ReactionStyle.ERROR
            )
        } else {
            saveExpense()
        }
    }

    private fun saveExpense() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            //TODO
            if (state.value.receipt == null) return@launch
            if (state.value.id == null) {
                addExpenseUseCase.invoke(
                    type = state.value.type,
                    name = state.value.name,
                    sum = state.value.sum.toDouble(),
                    receipt = state.value.receipt!!,
                )
            } else {
                //todo update current expense
            }
            clearState()
            reactUseCase.invoke(
                title = context.getString(R.string.add_expense_popup_success),
                style = ReactionStyle.SUCCESS
            )
            _state.update { it.copy(action = AddExpenseState.Action.BACK) }
        } catch (e: Exception) {
            reactUseCase.invoke(
                title = context.getString(R.string.add_expense_popup_error),
                style = ReactionStyle.SUCCESS
            )
        }
        _state.update { it.copy(loading = false) }
    }
}
