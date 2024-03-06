package com.github.linkav20.info.presentation.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.info.R
import java.time.OffsetDateTime

@Composable
fun EditableInfoComponent(
    name: String?,
    startTime: OffsetDateTime?,
    endTime: OffsetDateTime?,
    showButton: Boolean = false,
    onNameChanged: (String) -> Unit,
    onStartDateChanged: (OffsetDateTime) -> Unit,
    onEndDateChanged: (OffsetDateTime) -> Unit,
    onSaveButtonClick: (() -> Unit)? = null
) = TamadaCard {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.creation_party_screen_info_block_title),
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.creation_party_screen_info_block_description),
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFiledWithTitle(
            value = name,
            title = stringResource(id = R.string.creation_party_screen_info_name_title),
            hint = stringResource(id = R.string.creation_party_screen_info_name_hint),
            onValueChanged = onNameChanged,
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateTimeWithTitle(
            value = startTime,
            title = stringResource(id = R.string.creation_party_screen_info_start_datetime_title),
            hint = stringResource(id = R.string.creation_party_screen_info_start_datetime_mask),
            onValueChanged = onStartDateChanged,
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateTimeWithTitle(
            value = endTime,
            title = stringResource(id = R.string.creation_party_screen_info_end_datetime_title),
            hint = stringResource(id = R.string.creation_party_screen_info_end_datetime_mask),
            onValueChanged = onEndDateChanged,
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