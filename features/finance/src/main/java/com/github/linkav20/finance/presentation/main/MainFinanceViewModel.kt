package com.github.linkav20.finance.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.finance.domain.usecase.LoadFinanceStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFinanceViewModel @Inject constructor(
    private val loadFinanceStateUseCase: LoadFinanceStateUseCase,
    private val getPartyIdUseCase: GetPartyIdUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MainFinanceState> =
        MutableStateFlow(MainFinanceState.Loading)
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onCloseDialog() =
        _state.update { (it as MainFinanceState.BasicState).copy(showDialog = false) }

    fun onOpenDialog() =
        _state.update { (it as MainFinanceState.BasicState).copy(showDialog = true) }

    fun onConfirmDialog() =
        _state.update { (it as MainFinanceState.BasicState).copy(showDialog = true) }


    private fun loadData() = viewModelScope.launch {
        val id = getPartyIdUseCase.invoke() ?: return@launch
        val state = loadFinanceStateUseCase.invoke(id)
        _state.update { MainFinanceState.BasicState(false) }
    }
}