package com.github.linkav20.finance.presentation.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingState())
    val state = _state.asStateFlow()

    fun onNextClick() {
        val currentIndex = state.value.step.index
        val newStep = OnboardingState.Step.values().find { it.index == currentIndex + 1 }
        if (newStep != null) {
            _state.update { it.copy(step = newStep) }
        } else {
            _state.update { it.copy(action = OnboardingState.Action.BACK) }
        }
    }

    fun onPrevClick() {
        val currentIndex = state.value.step.index
        val newStep = OnboardingState.Step.values().find { it.index == currentIndex - 1 }
        if (newStep != null) {
            _state.update { it.copy(step = newStep) }
        }
    }
}
