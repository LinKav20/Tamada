package com.github.linkav20.finance.presentation.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor

@Composable
fun TurnOnFinanceDialog(
    onClose: () -> Unit,
    onConfirm: () -> Unit
) = AlertDialog(
    onDismissRequest = onClose,
    title = {
        Text(
            text = "Подтверждение действия",
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader
        )
    },
    text = {
        Text(
            text = "Вы действительно хотите удалить выбранный элемент?",
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain
        )
    },
    dismissButton = {
        Text(
            modifier = Modifier
                .clickable { onClose() }
                .padding(16.dp),
            text = "Cancel",
            style = TamadaTheme.typography.body,
            color = TamadaTheme.colors.textMain
        )
    },
    confirmButton = {
        Text(
            modifier = Modifier
                .clickable { onConfirm() }
                .padding(16.dp),
            text = "OK",
            style = TamadaTheme.typography.body,
            color = getPrimaryColor(scheme = ColorScheme.FINANCE)
        )
    },
    shape = TamadaTheme.shapes.medium,
    backgroundColor = TamadaTheme.colors.backgroundPrimary,
)
