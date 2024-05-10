package com.github.linkav20.finance.presentation.progress

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.UserUI
import com.github.linkav20.finance.domain.usecase.GetAllUsersWithExpenses
import com.github.linkav20.finance.domain.usecase.GetTotalPartySumUseCase
import com.github.linkav20.finance.navigation.ProgressDestination
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
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(ProgressState())
    val state = _state.asStateFlow()

    init {
        val step = ProgressDestination.extractStep(savedStateHandle)
        _state.update { it.copy(step = step) }
        loadData()
    }

    fun onExpandUserExpenseClick(userUI: UserUI) {
        val newUser = userUI.copy(isExpanded = !userUI.isExpanded)
        val index = _state.value.users.indexOf(userUI)
        val newList = _state.value.users.minus(userUI).toMutableList()
        newList.add(index, newUser)
        _state.update { it.copy(users = newList) }
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

        } finally {
            _state.update { it.copy(loading = false) }
        }
    }
}
