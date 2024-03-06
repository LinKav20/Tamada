package com.github.linkav20.info.presentation.create

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
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaSwitchButton
import com.github.linkav20.info.R
import com.github.linkav20.info.presentation.shared.EditableInfoComponent
import java.time.OffsetDateTime

@Composable
fun MainContent(
    state: CreationPartyState,
    modifier: Modifier = Modifier,
    onExpensesButtonClick: () -> Unit,
    onNameChanged: (String) -> Unit,
    onStartDateChanged: (OffsetDateTime) -> Unit,
    onEndDateChanged: (OffsetDateTime) -> Unit,
) = Column(
    modifier = modifier,
) {
    EditableInfoComponent(
        name = state.name,
        startTime = state.startTime,
        endTime = state.endTime,
        onNameChanged = onNameChanged,
        onStartDateChanged = onStartDateChanged,
        onEndDateChanged = onEndDateChanged
    )
    Spacer(modifier = Modifier.height(16.dp))
    TamadaCard {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.creation_party_screen_info_expenses_title),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.creation_party_screen_info_expenses_description),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain,
            )
            Spacer(modifier = Modifier.height(16.dp))
            TamadaSwitchButton(
                checkVariant = stringResource(id = R.string.creation_party_screen_expenses_turn_on),
                uncheckVariant = stringResource(id = R.string.creation_party_screen_expenses_turn_off),
                isChecked = state.isExpenses,
                onClick = onExpensesButtonClick,
            )
        }
    }
}
