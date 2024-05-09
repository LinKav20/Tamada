package com.github.linkav20.finance.presentation.main

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
import com.github.linkav20.finance.domain.usecase.LoadFinanceStateUseCase
import com.github.linkav20.finance.domain.usecase.UpdateFinanceStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFinanceViewModel @Inject constructor(
    private val loadFinanceStateUseCase: LoadFinanceStateUseCase,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    private val turnOnFinanceUseCase: UpdateFinanceStateUseCase,
    private val reactUseCase: ReactUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state: MutableStateFlow<MainFinanceState> =
        MutableStateFlow(MainFinanceState())
    val state = _state.asStateFlow()

    fun onStart() = loadData()

    fun onRetry() {
        _state.update { it.copy(error = null) }
        loadData()
    }

    fun onCloseDialog() =
        _state.update { it.copy(showDialog = false) }

    fun onOpenDialog() =
        _state.update { it.copy(showDialog = true) }

    fun onConfirmDialog() = viewModelScope.launch {
        onCloseDialog()
        _state.update { it.copy(loading = true) }
        try {
            val id = getPartyIdUseCase.invoke() ?: return@launch
            turnOnFinanceUseCase.invoke(
                state = FinanceState.STEP_1,
                id = id
            )
            _state.update { it.copy(tab = MainFinanceState.Tab.STEP1) }
            reactUseCase.invoke(
                title = context.getString(R.string.basic_finance_turn_on_push_title),
                style = ReactionStyle.SUCCESS
            )
        } catch (_: Exception) {
            reactUseCase.invoke(
                title = context.getString(R.string.basic_finance_turn_on_error_push_title),
                style = ReactionStyle.ERROR
            )
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }


    private fun loadData() = viewModelScope.launch {
        try {
            _state.update { it.copy(loading = true) }
            val id = getPartyIdUseCase.invoke() ?: return@launch
            when (loadFinanceStateUseCase.invoke(id)) {
                FinanceState.NONE -> {
                    val role = getRoleUseCase.invoke()
                    _state.update { it.copy(isManager = role == UserRole.MANAGER) }
                }

                FinanceState.STEP_1 -> {
                    _state.update { it.copy(tab = MainFinanceState.Tab.STEP1) }
                }

                FinanceState.STEP_2 -> {
                    _state.update { it.copy(tab = MainFinanceState.Tab.STEP2) }
                }

                FinanceState.STEP_3 -> {
                    _state.update { it.copy(tab = MainFinanceState.Tab.STEP3) }
                }
            }
        } catch (e: Exception) {
            _state.update { it.copy(error = e) }
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }
}
