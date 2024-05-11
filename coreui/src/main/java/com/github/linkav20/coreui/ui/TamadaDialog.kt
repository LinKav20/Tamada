package com.github.linkav20.coreui.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.R
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor

@Composable
fun TamadaDialog(
    title: String,
    body: @Composable () -> Unit,
    colorScheme: ColorScheme = ColorScheme.MAIN,
    onClose: () -> Unit,
    onConfirm: () -> Unit
) = AlertDialog(
    onDismissRequest = onClose,
    title = {
        Text(
            text = title,
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader
        )
    },
    text = body,
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
            color = getPrimaryColor(scheme = colorScheme)
        )
    },
    shape = TamadaTheme.shapes.medium,
    backgroundColor = TamadaTheme.colors.backgroundPrimary,
)
