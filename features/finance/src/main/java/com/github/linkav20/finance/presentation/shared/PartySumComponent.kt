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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.finance.R

@Composable
fun PartySumComponent(
    sum: Double,
    subtitle: String? = null,
    onClick: () -> Unit
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
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(id = R.string.step1_party_progress_title),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader
            )
            Text(
                text = stringResource(
                    id = R.string.step1_sum_formatter,
                    sum
                ),
                style = TamadaTheme.typography.body,
                color = TamadaTheme.colors.textMain,
            )
        }
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain,
            )
        }
        TamadaButton(
            title = stringResource(id = R.string.step1_party_progress_button),
            type = ButtonType.SECONDARY,
            colorScheme = ColorScheme.FINANCE,
            onClick = onClick
        )
    }
}