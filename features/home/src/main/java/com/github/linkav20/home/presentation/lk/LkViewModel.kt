package com.github.linkav20.home.presentation.lk

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class LkViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(LkState())
    val state = _state.asStateFlow()



}