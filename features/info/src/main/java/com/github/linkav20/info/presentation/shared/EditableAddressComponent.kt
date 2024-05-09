package com.github.linkav20.info.presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaTextFiled
import com.github.linkav20.info.R

@Composable
fun EditableAddressComponent(
    address: String?,
    addressAdditional: String?,
    addressLink:String?,
    showButton: Boolean = false,
    onAddressChanged: (String) -> Unit,
    onAddressAdditionChanged: (String) -> Unit,
    onAddressLinkChanged: (String) -> Unit,
    onSaveButtonClick: (() -> Unit)? = null
) = Column {
    TamadaCard {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.creation_party_screen_address_block_title),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.creation_party_screen_address_block_description),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain
            )
            Spacer(modifier = Modifier.height(16.dp))
            TamadaTextFiled(
                value = address ?: "",
                placeholder = stringResource(id = R.string.creation_party_screen_address_hint),
                onValueChange = onAddressChanged,
            )
            Spacer(modifier = Modifier.height(16.dp))
            TamadaTextFiled(
                value = addressLink ?: "",
                placeholder = stringResource(id = R.string.creation_party_screen_address_link_hint),
                onValueChange = onAddressLinkChanged,
            )
            if (showButton) {
                Spacer(modifier = Modifier.height(24.dp))
                TamadaButton(
                    title = stringResource(id = R.string.info_party_save_button),
                    onClick = { onSaveButtonClick?.invoke() }
                )
            }
        }
    }
    if (!showButton) {
        Spacer(modifier = Modifier.height(16.dp))
        TamadaCard {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.creation_party_screen_road_block_title),
                    style = TamadaTheme.typography.head,
                    color = TamadaTheme.colors.textHeader,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.creation_party_screen_road_block_description),
                    style = TamadaTheme.typography.caption,
                    color = TamadaTheme.colors.textMain,
                )
                Spacer(modifier = Modifier.height(16.dp))
                TamadaTextFiled(
                    value = addressAdditional ?: "",
                    placeholder = stringResource(id = R.string.creation_party_screen_road_hint),
                    onValueChange = onAddressAdditionChanged,
                )
            }
        }
    }
}
