package com.github.linkav20.info.presentation.info

import com.github.linkav20.core.utils.DateTimeUtils
import com.github.linkav20.info.domain.model.Party

data class PartyInfoState(
    val loading: Boolean = false,
    val error: Throwable? = null,
    val party: Party? = null,
    val canEdit: Boolean = true,
    val canInfoEdit: Boolean = false,
    val canAddressEdit: Boolean = false,
    val canImportantEdit: Boolean = false,
    val canThemeEdit: Boolean = false
) {
    val isInfoSectionEditable = canInfoEdit && canEdit
    val isAddressSectionEditable = canAddressEdit && canEdit
    val isImportantSectionEditable = canImportantEdit && canEdit
    val isThemeSectionEditable = canThemeEdit && canEdit

    val partyDate: String? = if (party?.startTime != null && party.endTime != null) {
        DateTimeUtils.toUiString(party.startTime, party.endTime)
    } else {
        if (party?.startTime != null) {
            DateTimeUtils.toUiString(party.startTime)
        } else if (party?.endTime != null) {
            DateTimeUtils.toUiString(party.endTime)
        } else {
            null
        }
    }
}
