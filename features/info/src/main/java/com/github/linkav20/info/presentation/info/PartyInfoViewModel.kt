package com.github.linkav20.info.presentation.info

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.info.R
import com.github.linkav20.info.domain.usecase.GetPartyUseCase
import com.github.linkav20.info.domain.usecase.SavePartyUseCase
import com.github.linkav20.info.domain.usecase.UpdatePartyAddressAdditionalUseCase
import com.github.linkav20.info.domain.usecase.UpdatePartyAddressLinkUseCase
import com.github.linkav20.info.domain.usecase.UpdatePartyAddressUseCase
import com.github.linkav20.info.domain.usecase.UpdatePartyDresscodeUseCase
import com.github.linkav20.info.domain.usecase.UpdatePartyEndTimeUseCase
import com.github.linkav20.info.domain.usecase.UpdatePartyImportantUseCase
import com.github.linkav20.info.domain.usecase.UpdatePartyMoodboardLinkUseCase
import com.github.linkav20.info.domain.usecase.UpdatePartyNameUseCase
import com.github.linkav20.info.domain.usecase.UpdatePartyStartTimeUseCase
import com.github.linkav20.info.domain.usecase.UpdatePartyThemeUseCase
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
    private val updatePartyThemeUseCase: UpdatePartyThemeUseCase,
    private val updatePartyStartTimeUseCase: UpdatePartyStartTimeUseCase,
    private val updatePartyMoodboardLinkUseCase: UpdatePartyMoodboardLinkUseCase,
    private val updatePartyImportantUseCase: UpdatePartyImportantUseCase,
    private val updatePartyEndTimeUseCase: UpdatePartyEndTimeUseCase,
    private val updatePartyDresscodeUseCase: UpdatePartyDresscodeUseCase,
    private val updatePartyAddressUseCase: UpdatePartyAddressUseCase,
    private val updatePartyAddressLinkUseCase: UpdatePartyAddressLinkUseCase,
    private val updatePartyAddressAdditionalUseCase: UpdatePartyAddressAdditionalUseCase,
    private val updatePartyNameUseCase: UpdatePartyNameUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(PartyInfoState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onEditInfoClick() = _state.update { it.copy(canInfoEdit = true) }

    fun onSaveInfoClick() {
        val name = state.value.party?.name
        if (name.isNullOrEmpty()) {
            emptyFormReact()
            return
        }
        invokeUseCase {
            updatePartyNameUseCase.invoke(name = name)
        }
        val startTime = state.value.party?.startTime
        if (startTime != null) {
            invokeUseCase {
                updatePartyStartTimeUseCase.invoke(startTime = startTime)
            }
        }
        val endTime = state.value.party?.endTime
        if (endTime != null) {
            invokeUseCase {
                updatePartyEndTimeUseCase.invoke(endTime = endTime)
            }
        }
        _state.update { it.copy(canInfoEdit = false) }
    }

    fun onEditAddressClick() = _state.update { it.copy(canAddressEdit = true) }

    fun onSaveAddressClick() {
        _state.update { it.copy(canAddressEdit = false) }
        val address = state.value.party?.address
        if (!address.isNullOrEmpty()) {
            invokeUseCase { updatePartyAddressUseCase.invoke(address = address) }
        }
        val addressAdditional = state.value.party?.addressAdditional
        if (!addressAdditional.isNullOrEmpty()) {
            invokeUseCase {
                updatePartyAddressAdditionalUseCase.invoke(
                    address = addressAdditional
                )
            }
        }
    }

    fun onEditImportantClick() = _state.update { it.copy(canImportantEdit = true) }

    fun onSaveImportantClick() {
        _state.update { it.copy(canImportantEdit = false) }
        val importance = state.value.party?.important
        if (!importance.isNullOrEmpty()) {
            invokeUseCase {
                updatePartyImportantUseCase.invoke(important = importance)
            }
        }
    }

    fun onEditThemeClick() = _state.update { it.copy(canThemeEdit = true) }

    fun onSaveThemeClick() {
        _state.update { it.copy(canThemeEdit = false) }
        val moodboard = state.value.party?.moodboadLink
        if (!moodboard.isNullOrEmpty()) {
            invokeUseCase {
                updatePartyMoodboardLinkUseCase.invoke(link = moodboard)
            }
        }
        val theme = state.value.party?.theme
        if (!theme.isNullOrEmpty()) {
            invokeUseCase {
                updatePartyThemeUseCase.invoke(theme = theme)
            }
        }
        val dresscode = state.value.party?.dressCode
        if (!dresscode.isNullOrEmpty()) {
            invokeUseCase {
                updatePartyDresscodeUseCase.invoke(dresscode = dresscode)
            }
        }
    }

    fun onNameChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(name = value)
        _state.update { it.copy(party = newParty) }
    }

    fun onStartDateChanged(value: OffsetDateTime) {
        val party = state.value.party ?: return
        val endTime = party.endTime
        if (endTime == null) {
            val newParty = party.copy(startTime = value)
            _state.update { it.copy(party = newParty) }
        } else {
            if (value >= endTime) {
                reactUseCase.invoke(
                    title = context.getString(R.string.info_party_start_time_error),
                    style = ReactionStyle.ERROR
                )
            } else {
                val newParty = party.copy(startTime = value)
                _state.update { it.copy(party = newParty) }
            }
        }
    }

    fun onEndDateChanged(value: OffsetDateTime) {
        val party = state.value.party ?: return
        val startTime = party.startTime
        if (startTime == null) {
            val newParty = party.copy(endTime = value)
            _state.update { it.copy(party = newParty) }
        } else {
            if (value <= startTime) {
                reactUseCase.invoke(
                    title = context.getString(R.string.creation_party_end_time_error),
                    style = ReactionStyle.ERROR
                )
            } else {
                val newParty = party.copy(endTime = value)
                _state.update { it.copy(party = newParty) }
            }
        }
    }

    fun onAddressChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(address = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
    }

    fun onAddressAdditionalChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(addressAdditional = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
    }

    fun onImportantChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(important = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
    }

    fun onMoodboardinkChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(moodboadLink = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
    }

    fun onThemeChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(theme = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
    }

    fun onDressCodeChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(dressCode = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
    }

    fun onAddressLinkChanged(value: String) {
        val party = state.value.party ?: return
        val newParty = party.copy(addressLink = value.ifEmpty { null })
        _state.update { it.copy(party = newParty) }
        invokeUseCase {
            updatePartyAddressLinkUseCase.invoke(
                partyId = party.id,
                link = value
            )
        }
    }

    fun onRetry() = loadData()

    private fun saveParty() = viewModelScope.launch {
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

    private fun invokeUseCase(action: suspend () -> Unit) = viewModelScope.launch {
        try {
            action.invoke()
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        }
    }

    private fun loadData() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            val id = getPartyIdUseCase.invoke()
            if (id == null) {
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

    private fun emptyFormReact() = reactUseCase.invoke(
        title = context.getString(R.string.info_party_empty_form_error),
        style = ReactionStyle.ERROR
    )
}
