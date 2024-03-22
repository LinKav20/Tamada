package com.github.linkav20.info.presentation.info

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.DomainException
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.info.R
import com.github.linkav20.info.domain.model.Party
import com.github.linkav20.info.domain.usecase.GetPartyUseCase
import com.github.linkav20.info.domain.usecase.SavePartyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class PartyInfoViewModel @Inject constructor(
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val getPartyUseCase: GetPartyUseCase,
    private val reactUseCase: ReactUseCase,
    private val savePartyUseCase: SavePartyUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(PartyInfoState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onEditInfoClick() = _state.update { it.copy(canInfoEdit = true) }

    fun onSaveInfoClick() = _state.update { it.copy(canInfoEdit = false) }

    fun onEditAddressClick() = _state.update { it.copy(canAddressEdit = true) }

    fun onSaveAddressClick() = _state.update { it.copy(canAddressEdit = false) }

    fun onEditImportantClick() = _state.update { it.copy(canImportantEdit = true) }

    fun onSaveImportantClick() = _state.update { it.copy(canImportantEdit = false) }

    fun onEditThemeClick() = _state.update { it.copy(canThemeEdit = true) }

    fun onSaveThemeClick() = _state.update { it.copy(canThemeEdit = false) }

    fun onNameChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(name = value)
        _state.update { it.copy(party = newParty) }
        saveParty()
    }

    fun onStartDateChanged(value: OffsetDateTime) {
        val party = state.value.party ?: return
        val endTime = party.endTime
        if (endTime == null) {
            val newParty = party.copy(startTime = value)
            _state.update { it.copy(party = newParty) }
            saveParty()
        } else {
            if (value >= endTime) {
                reactUseCase.invoke(
                    title = context.getString(R.string.info_party_start_time_error),
                    style = ReactionStyle.ERROR
                )
            } else {
                val newParty = party.copy(startTime = value)
                _state.update { it.copy(party = newParty) }
                saveParty()
            }
        }
    }

    fun onEndDateChanged(value: OffsetDateTime) {
        val party = state.value.party ?: return
        val startTime = party.startTime
        if (startTime == null) {
            val newParty = party.copy(endTime = value)
            _state.update { it.copy(party = newParty) }
            saveParty()
        } else {
            if (value <= startTime) {
                reactUseCase.invoke(
                    title = context.getString(R.string.creation_party_end_time_error),
                    style = ReactionStyle.ERROR
                )
            } else {
                val newParty = party.copy(endTime = value)
                _state.update { it.copy(party = newParty) }
                saveParty()
            }
        }
    }

    fun onAddressChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(address = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
        saveParty()
        loadData()
    }

    fun onAddressAdditionalChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(addressAdditional = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
        saveParty()
    }

    fun onImportantChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(important = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
        saveParty()
    }

    fun onMoodboardinkChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(moodboadLink = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
        saveParty()
    }

    fun onThemeChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(theme = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
        saveParty()
    }

    fun onDressCodeChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(dressCode = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
        saveParty()
    }

    fun onRetry() = loadData()

    fun saveParty() = viewModelScope.launch {
        val party = state.value.party ?: return@launch
        try {
            savePartyUseCase.invoke(party)
        } catch (e: Exception) {
            reactUseCase.invoke(
                title = context.getString(R.string.info_party_save_party_error_title),
                style = ReactionStyle.ERROR
            )
        }
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val id = getPartyIdUseCase.invoke()
            if (id == null) {
                _state.update { it.copy(error = DomainException.Unknown) }
                return@launch
            }
            val party = getPartyUseCase.invoke(id)
            val role = getRoleUseCase.invoke()
            _state.update {
                it.copy(
                    party = party,
                    canEdit = role == UserRole.MANAGER
                )
            }
        } catch (e: Throwable) {
            _state.update { it.copy(error = e) }
        }
        _state.update { it.copy(loading = false) }
    }
}
