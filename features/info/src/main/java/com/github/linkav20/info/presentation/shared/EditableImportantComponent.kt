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
import com.github.linkav20.coreui.ui.TamadaTextFiled
import com.github.linkav20.info.R

@Composable
fun EditableImportantComponent(
    important: String?,
    showButton: Boolean = false,
    onSaveButtonClick: (() -> Unit)? = null,
    onImportantChanged: (String) -> Unit
) = TamadaCard {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.creation_party_screen_important_block_title),
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.creation_party_screen_important_block_description),
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TamadaTextFiled(
            value = important ?: "",
            placeholder = stringResource(id = R.string.creation_party_screen_important_hint),
            onValueChange = onImportantChanged,
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
