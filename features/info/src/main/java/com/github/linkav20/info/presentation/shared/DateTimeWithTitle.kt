package com.github.linkav20.info.presentation.shared

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.linkav20.core.utils.DateTimeUtils
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaDateTimePickerElement
import com.github.linkav20.coreui.ui.TamadaTextWithBackground
import com.github.linkav20.coreui.ui.TextWithBackgroundType
import java.time.OffsetDateTime

@Composable
fun DateTimeWithTitle(
    value: OffsetDateTime?,
    title: String,
    hint: String,
    onValueChanged: (OffsetDateTime) -> Unit,
) {
    Text(
        text = title,
        style = TamadaTheme.typography.body.copy(fontWeight = FontWeight.Bold),
        color = TamadaTheme.colors.textHeader,
    )
    Spacer(modifier = Modifier.height(4.dp))
    TamadaDateTimePickerElement(
        currentDate = value,
        onValueChanged = onValueChanged,
        content = {
            TamadaTextWithBackground(
                modifier = Modifier.fillMaxWidth(),
                type = TextWithBackgroundType.FIELD,
                textColor = if (value == null) {
                    TamadaTheme.colors.textHint
                } else {
                    null
                },
                text = if (value != null) {
                    DateTimeUtils.toUiString(value)
                } else {
                    hint
                }
            )
        }
    )
}
