package com.github.linkav20.finance.presentation.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.linkav20.core.utils.DateTimeUtils
import com.github.linkav20.coreui.R as CoreUi
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaDateTimePickerElement
import com.github.linkav20.coreui.ui.TamadaTextWithBackground
import com.github.linkav20.coreui.ui.TextWithBackgroundType
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.finance.R
import java.time.OffsetDateTime

@Composable
fun DeadlineComponent(
    isManager: Boolean,
    deadline: OffsetDateTime? = null,
    onEditDeadlineClick: () -> Unit,
    onEndStepClick: () -> Unit
) = TamadaCard(
    colorScheme = ColorScheme.FINANCE
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.step1_deadline_title),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader,
            )
            if (isManager) {
                TamadaButton(
                    colorScheme = ColorScheme.FINANCE,
                    iconPainter = painterResource(id = CoreUi.drawable.edit_icon),
                    type = ButtonType.SECONDARY,
                    onClick = onEditDeadlineClick,
                )
            }
        }
        if (deadline == null) {
            Text(
                text = stringResource(id = R.string.step1_deadline_nullable_text),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain
            )
        } else {
            Text(
                text = DateTimeUtils.toUiString(deadline),
                style = TamadaTheme.typography.head,
                color = getPrimaryColor(scheme = ColorScheme.FINANCE)
            )
        }
        if (isManager) {
            TamadaButton(
                title = stringResource(id = R.string.step1_deadline_button),
                type = ButtonType.SECONDARY,
                colorScheme = ColorScheme.FINANCE,
                onClick = onEndStepClick
            )
        }
    }
}

@Composable
fun EditableDeadlineComponent(
    deadline: OffsetDateTime? = null,
    onDeadlineChanged: (OffsetDateTime) -> Unit,
    onDeadlineSet: () -> Unit
) = TamadaCard(
    colorScheme = ColorScheme.FINANCE
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.step1_deadline_title),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader,
            )
        }
        TamadaDateTimePickerElement(
            currentDate = deadline,
            onValueChanged = onDeadlineChanged,
            content = {
                TamadaTextWithBackground(
                    colorScheme = ColorScheme.FINANCE,
                    modifier = Modifier.fillMaxWidth(),
                    type = TextWithBackgroundType.FIELD,
                    textColor = if (deadline == null) {
                        TamadaTheme.colors.textHint
                    } else {
                        null
                    },
                    text = if (deadline != null) {
                        DateTimeUtils.toUiString(deadline)
                    } else {
                        stringResource(id = com.github.linkav20.finance.R.string.step1_deadline_nullable_text)
                    }
                )
            }
        )
        TamadaButton(
            colorScheme = ColorScheme.FINANCE,
            title = stringResource(id = R.string.step1_deadline_set_button),
            type = ButtonType.SECONDARY,
            onClick = onDeadlineSet
        )
    }
}

