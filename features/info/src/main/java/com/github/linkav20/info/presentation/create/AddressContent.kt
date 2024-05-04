package com.github.linkav20.info.presentation.create

import androidx.compose.runtime.Composable
import com.github.linkav20.info.presentation.shared.EditableAddressComponent

@Composable
fun AddressContent(
    state: CreationPartyState,
    onAddressLinkChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onAddressAdditionChanged: (String) -> Unit,
) = EditableAddressComponent(
    address = state.address,
    addressAdditional = state.addressAdditional,
    addressLink = state.addressLink,
    onAddressChanged = onAddressChanged,
    onAddressAdditionChanged = onAddressAdditionChanged,
    onAddressLinkChanged = onAddressLinkChanged
)
