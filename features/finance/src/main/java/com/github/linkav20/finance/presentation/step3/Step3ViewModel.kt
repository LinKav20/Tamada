package com.github.linkav20.finance.presentation.step3

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.finance.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Step3ViewModel @Inject constructor(
    private val reactUseCase: ReactUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(Step3State())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onDeptDoneClick() {
        _state.update { it.copy(isDept = !state.value.isDept) }
        reactUseCase.invoke(
            title = context.getString(R.string.step3_dept_done_success_popup),
            style = ReactionStyle.SUCCESS
        )
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        _state.update { it.copy(loading = false) }
    }
}