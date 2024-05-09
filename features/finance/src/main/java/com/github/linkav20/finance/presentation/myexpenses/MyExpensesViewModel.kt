package com.github.linkav20.finance.presentation.myexpenses

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.core.utils.saveTempFileAndGetUri
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.usecase.DeleteExpenseUseCase
import com.github.linkav20.finance.domain.usecase.GetExpenseReceiptUseCase
import com.github.linkav20.finance.domain.usecase.GetMyExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FILE_FORMAT = ".pdf"

@HiltViewModel
class MyExpensesViewModel @Inject constructor(
    private val getMyExpenseUseCase: GetMyExpenseUseCase,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val reactUseCase: ReactUseCase,
    private val getExpenseReceiptUseCase: GetExpenseReceiptUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(MyExpensesState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onSelectExpense(expense: Expense? = null) {
        _state.update { it.copy(selectExpense = expense) }
    }

    fun onDeleteButtonClick() = _state.update { it.copy(showDialog = true) }

    fun onCloseDialogClick() = _state.update { it.copy(showDialog = false) }

    fun onDeleteExpenseClick() = viewModelScope.launch {
        _state.update { it.copy(showDialog = false, loading = true) }
        try {
            if (state.value.selectExpense != null) {
                deleteExpenseUseCase.invoke(state.value.selectExpense!!.id)
                reactUseCase.invoke(
                    context.getString(R.string.my_expense_popup_expense_delete_success),
                    style = ReactionStyle.SUCCESS
                )
            }
        } catch (_: Exception) {
            reactUseCase.invoke(
                context.getString(R.string.my_expense_popup_expense_delete_error),
                style = ReactionStyle.ERROR
            )
        }
        _state.update { it.copy(loading = false) }
    }

    fun onReceiptClick(expense: Expense) = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val receipt = getExpenseReceiptUseCase.invoke(expense.id)
            _state.update {
                it.copy(
                    uri = saveTempFileAndGetUri(
                        context = context,
                        data = receipt,
                        extension = FILE_FORMAT
                    )
                )
            }
        } catch (_: Exception) {
            reactUseCase.invoke(
                context.getString(R.string.my_expense_popup_receipt_not_loaded),
                style = ReactionStyle.ERROR
            )
        }
        _state.update { it.copy(loading = false) }
    }

    fun nullifyUri() = _state.update { it.copy(uri = null) }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val expenses = getMyExpenseUseCase.invoke()
            _state.update { it.copy(expenses = expenses) }
        } catch (_: Exception) {

        }
        _state.update { it.copy(loading = false) }
    }
}
