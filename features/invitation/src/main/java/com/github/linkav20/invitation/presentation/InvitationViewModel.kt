package com.github.linkav20.invitation.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.linkav20.core.domain.entity.DomainException
import com.github.linkav20.invitation.domain.usecase.AddUserToPartyUseCase
import com.github.linkav20.invitation.domain.usecase.GetPartyInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitationViewModel @Inject constructor(
    private val getPartyInfoUseCase: GetPartyInfoUseCase,
    private val addUserToPartyUseCase: AddUserToPartyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(InvitationState())
    val state = _state.asStateFlow()

    fun loadData(partyId: Int) = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val party = getPartyInfoUseCase.invoke(partyId)
            _state.update { it.copy(party = party) }
        } catch (e: Exception) {
            _state.update { it.copy(exception = e) }
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }

    fun addUserToParty() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val partyId = state.value.party?.id ?: return@launch
            addUserToPartyUseCase.invoke(partyId = partyId)
            _state.update { it.copy(action = InvitationState.Action.BACK) }
        } catch (e: Exception) {
            _state.update { it.copy(exception = e) }
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }
}
