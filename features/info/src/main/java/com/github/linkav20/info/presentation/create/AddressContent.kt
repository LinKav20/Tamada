package com.github.linkav20.info.presentation.create

import androidx.compose.runtime.Composable
import com.github.linkav20.info.presentation.shared.EditableAddressComponent

@Composable
fun AddressContent(
    state: CreationPartyState,
    onAddressChanged: (String) -> Unit,
    onAddressAdditionChanged: (String) -> Unit,
) = EditableAddressComponent(
    address = state.address,
    addressAdditional = state.addressAdditional,
    onAddressChanged = onAddressChanged,
    onAddressAdditionChanged = onAddressAdditionChanged
)
