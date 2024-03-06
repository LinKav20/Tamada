package com.github.linkav20.auth.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthMainScreenViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(AuthMainScreenState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(loading = false) }
    }
}
