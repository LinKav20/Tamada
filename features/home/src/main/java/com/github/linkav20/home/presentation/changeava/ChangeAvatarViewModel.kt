package com.github.linkav20.home.presentation.changeava

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.home.domain.usecase.UpdateUserAvatarUseCase
import com.github.linkav20.home.navigation.ChangeAvatarDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeAvatarViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateUserAvatarUseCase: UpdateUserAvatarUseCase,
    private val reactUseCase: ReactUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ChangeAvatarState())
    val state = _state.asStateFlow()

    init {
        val id = ChangeAvatarDestination.extractId(savedStateHandle)
        _state.update { it.copy(id = id) }
    }

    fun onChangeAvatar(id: Int) = viewModelScope.launch {
        try {
            _state.update { it.copy(loading = true) }
            updateUserAvatarUseCase.invoke(id)
            _state.update { it.copy(id = id) }
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }
}
