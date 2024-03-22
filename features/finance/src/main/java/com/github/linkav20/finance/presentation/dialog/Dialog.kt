package com.github.linkav20.finance.presentation.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.finance.R

@Composable
fun Dialog(
    subtitle: String,
    onClose: () -> Unit,
    onConfirm: () -> Unit
) = AlertDialog(
    onDismissRequest = onClose,
    title = {
        Text(
            text = stringResource(id = R.string.dialog_title),
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader
        )
    },
    text = {
        Text(
            text = subtitle,
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain
        )
    },
    dismissButton = {
        Text(
            modifier = Modifier
                .clickable { onClose() }
                .padding(16.dp),
            text = stringResource(id = R.string.dialog_cancel_button),
            style = TamadaTheme.typography.body,
            color = TamadaTheme.colors.textMain
        )
    },
    confirmButton = {
        Text(
            modifier = Modifier
                .clickable { onConfirm() }
                .padding(16.dp),
            text = stringResource(id = R.string.dialog_turn_on_button),
            style = TamadaTheme.typography.body,
            color = getPrimaryColor(scheme = ColorScheme.FINANCE)
        )
    },
    shape = TamadaTheme.shapes.medium,
    backgroundColor = TamadaTheme.colors.backgroundPrimary,
)
