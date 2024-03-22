package com.github.linkav20.info.presentation.shared

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaTextFiled
import com.github.linkav20.info.R

@Composable
fun EditableThemeComponent(
    moodboadLink: String?,
    theme: String?,
    dressCode: String?,
    showButton: Boolean = false,
    onSaveButtonClick: (() -> Unit)? = null,
    onThemeChanged: (String) -> Unit,
    onDressCodeChanged: (String) -> Unit,
    onMoodboardinkChanged: (String) -> Unit
) = TamadaCard {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.creation_party_screen_theme_block_title),
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.creation_party_screen_theme_block_description),
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain,
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextFiledWithTitleAndButton(
            value = theme,
            title = stringResource(id = R.string.creation_party_screen_theme_theme_title),
            hint = stringResource(id = R.string.creation_party_screen_theme_theme_hint),
            onValueChanged = onThemeChanged,
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextFiledWithTitleAndButton(
            value = dressCode,
            title = stringResource(id = R.string.creation_party_screen_theme_dresscode_title),
            hint = stringResource(id = R.string.creation_party_screen_theme_dresscode_hint),
            onValueChanged = onDressCodeChanged,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = R.string.creation_party_screen_theme_link_title),
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.creation_party_screen_theme_link_description),
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TamadaTextFiled(
            value = moodboadLink ?: "",
            placeholder = stringResource(id = R.string.creation_party_screen_theme_link_hint),
            onValueChange = onMoodboardinkChanged,
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

@Composable
private fun TextFiledWithTitleAndButton(
    value: String?,
    title: String,
    hint: String,
    onValueChanged: (String) -> Unit,
) = Column {
    Text(
        text = title,
        style = TamadaTheme.typography.body.copy(fontWeight = FontWeight.Bold),
        color = TamadaTheme.colors.textHeader,
    )
    Spacer(modifier = Modifier.height(4.dp))
    BoxWithConstraints {
        val height = this.maxHeight
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TamadaTextFiled(
                modifier = Modifier.weight(1f),
                value = value ?: "",
                placeholder = hint,
                onValueChange = onValueChanged
            )
//            Spacer(modifier = Modifier.width(4.dp))
//            TamadaButton(
//                modifier = Modifier.size(height),
//                onClick = { /*TODO*/ },
//                iconPainter = painterResource(id = com.github.linkav20.coreui.R.drawable.add_image_icon),
//                type = ButtonType.SECONDARY
//            )
        }
    }
}
