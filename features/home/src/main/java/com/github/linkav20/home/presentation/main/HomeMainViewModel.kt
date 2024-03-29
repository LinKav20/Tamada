package com.github.linkav20.home.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.auth.domain.usecase.GetAuthTokensUseCase
import com.github.linkav20.core.domain.usecase.SetPartyIdUseCase
import com.github.linkav20.home.domain.model.Party
import com.github.linkav20.home.domain.usecase.GetAllPartiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(
    private val getAllPartiesUseCase: GetAllPartiesUseCase,
    private val setPartyIdUseCase: SetPartyIdUseCase,
    private val getAuthTokensUseCase: GetAuthTokensUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeMainScreenState())
    val state = _state.asStateFlow()

    init {
        //loadData()
    }

    fun onPartyClick(party: Party) {
        setPartyIdUseCase.invoke(party.id)
        _state.update { it.copy(action = HomeMainScreenState.Action.PARTY) }
    }

    fun nullifyAction() = _state.update { it.copy(action = null) }

    fun getData() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        delay(1000)
        if (getAuthTokensUseCase.invoke() != null) {
            loadData()
        } else {
            _state.update { it.copy(action = HomeMainScreenState.Action.AUTH) }
        }
    }

    private fun loadData() = viewModelScope.launch {
        val parties = getAllPartiesUseCase.invoke()
        _state.update {
            it.copy(
                loading = false,
                parties = parties
            )
        }
    }
}
