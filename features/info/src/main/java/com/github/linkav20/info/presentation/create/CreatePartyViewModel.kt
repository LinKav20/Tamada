package com.github.linkav20.info.presentation.create

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.info.R
import com.github.linkav20.info.domain.model.Party
import com.github.linkav20.info.domain.usecase.SavePartyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class CreatePartyViewModel
@Inject
constructor(
    private val reactUseCase: ReactUseCase,
    private val savePartyUseCase: SavePartyUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(CreationPartyState())
    val state = _state.asStateFlow()

    fun onRoadMapClicks() =
        listOf(
            { },
            { },
            { },
            { },
        )

    fun onNameChanged(value: String) = _state.update { it.copy(name = value) }

    fun onStartDateChanged(value: OffsetDateTime) {
        val endTime = state.value.endTime
        if (endTime == null) {
            _state.update { it.copy(startTime = value) }
        } else {
            if (value >= state.value.startTime) {
                reactUseCase.invoke(
                    title = context.getString(R.string.creation_party_start_time_error),
                    style = ReactionStyle.ERROR
                )
            } else {
                _state.update { it.copy(startTime = value) }
            }
        }
    }

    fun onEndDateChanged(value: OffsetDateTime) {
        val startTime = state.value.startTime
        if (startTime == null) {
            _state.update { it.copy(endTime = value) }
        } else {
            if (value <= state.value.startTime) {
                reactUseCase.invoke(
                    title = context.getString(R.string.creation_party_end_time_error),
                    style = ReactionStyle.ERROR
                )
            } else {
                _state.update { it.copy(endTime = value) }
            }
        }
    }

    fun onAddressChanged(value: String) =
        _state.update { it.copy(address = value.ifEmpty { null }) }

    fun onAddressAdditionalChanged(value: String) =
        _state.update { it.copy(addressAdditional = value.ifEmpty { null }) }

    fun onImportantChanged(value: String) =
        _state.update { it.copy(important = value.ifEmpty { null }) }

    fun onMoodboardinkChanged(value: String) =
        _state.update { it.copy(moodboadLink = value.ifEmpty { null }) }

    fun onThemeChanged(value: String) = _state.update { it.copy(theme = value.ifEmpty { null }) }

    fun onDressCodeChanged(value: String) =
        _state.update { it.copy(dressCode = value.ifEmpty { null }) }

    fun onExpensesButtonClick() = _state.update { it.copy(isExpenses = !state.value.isExpenses) }

    fun nullifyAction() = _state.update { it.copy(action = null) }

    fun onNextClick() {
        val currentIndex = state.value.tab.index
        val newTab = CreationPartyState.Tab.values().find { it.index == currentIndex + 1 }
        if (newTab != null) {
            _state.update { it.copy(tab = newTab) }
        } else {
            createParty()
        }
    }

    fun onPrevClick() {
        val currentIndex = state.value.tab.index
        val newTab = CreationPartyState.Tab.values().find { it.index == currentIndex - 1 }
        if (state.value.isPreviousStep && newTab != null) {
            _state.update { it.copy(tab = newTab) }
        }
    }

    private fun onMainRoadMapClick() {
        reactUseCase.invoke(
            title = context.getString(R.string.creation_party_result_success),
            style = ReactionStyle.INFO,
        )
    }

    private fun onAddressRoadMapClick() {
        reactUseCase.invoke(
            title = context.getString(R.string.creation_party_result_success),
            style = ReactionStyle.ERROR,
        )
    }

    private fun onImportantClick() {
        reactUseCase.invoke(
            title = context.getString(R.string.creation_party_result_success),
            subtitle = "lemrlekgmle",
            style = ReactionStyle.WARNING,
        )
    }

    private fun onThemeRoadMapClick() {
        reactUseCase.invoke(
            title = context.getString(R.string.creation_party_result_success),
            style = ReactionStyle.SUCCESS,
        )
    }

    private fun createParty() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        try {
            savePartyUseCase.invoke(
                Party(
                    name = state.value.name.orEmpty(),
                    startTime = state.value.startTime,
                    endTime = state.value.endTime,
                    address = state.value.address,
                    addressAdditional = state.value.addressAdditional,
                    isExpenses = state.value.isExpenses,
                    important = state.value.important,
                    theme = state.value.theme,
                    dressCode = state.value.dressCode,
                    moodboadLink = state.value.moodboadLink,
                ),
            )
            reactUseCase.invoke(
                title = context.getString(R.string.creation_party_result_success),
                style = ReactionStyle.SUCCESS
            )
            _state.update { it.copy(action = CreationPartyState.Action.BACK) }
        } catch (e: Exception) {
            reactUseCase.invoke(
                title = context.getString(R.string.creation_party_result_error),
                style = ReactionStyle.ERROR
            )
        }
        _state.update { it.copy(loading = false) }
    }
}
