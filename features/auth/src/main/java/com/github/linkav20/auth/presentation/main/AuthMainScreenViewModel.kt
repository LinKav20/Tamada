package com.github.linkav20.auth.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.auth.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthMainScreenViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AuthMainScreenState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        logoutUseCase.invoke()
        _state.update { it.copy(loading = false) }
    }
}
