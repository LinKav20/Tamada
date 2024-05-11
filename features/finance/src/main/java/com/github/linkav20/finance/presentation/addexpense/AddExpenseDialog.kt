package com.github.linkav20.finance.presentation.addexpense

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaLoader
import com.github.linkav20.coreui.ui.TamadaTextFiled
import com.github.linkav20.coreui.ui.TamadaTextWithBackground
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.Expense

private const val PDF_FORMAT = "application/pdf"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddExpenseDialog(
    viewModel: AddExpenseViewModel,
    expense: Expense? = null,
    expenseType: Expense.Type = Expense.Type.SUM,
    onAddClick: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val openDocument =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            viewModel.onReceiptChange(uri)
        }

    LaunchedEffect(expense) {
        viewModel.initState(expense)
    }

    LaunchedEffect(expenseType) {
        viewModel.initExpenseType(expenseType)
    }

    if (state.loading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            TamadaLoader(scheme = ColorScheme.FINANCE)
        }
    } else {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = when (state.actionType) {
                    AddExpenseState.ActionType.ADD -> stringResource(id = R.string.add_expense_dialog_title)
                    AddExpenseState.ActionType.UPDATE -> stringResource(id = R.string.update_expense_dialog_title)
                },
                color = TamadaTheme.colors.textHeader,
                style = TamadaTheme.typography.head
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.add_expense_dialog_name_title),
                color = TamadaTheme.colors.textMain,
                style = TamadaTheme.typography.body
            )
            Spacer(modifier = Modifier.height(4.dp))
            TamadaTextFiled(
                value = state.name,
                placeholder = stringResource(id = R.string.add_expense_dialog_name_hint),
                onValueChange = viewModel::onNameChanged,
                colorScheme = ColorScheme.FINANCE,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.add_expense_dialog_sum_title),
                color = TamadaTheme.colors.textMain,
                style = TamadaTheme.typography.body
            )
            Spacer(modifier = Modifier.height(4.dp))
            TamadaTextFiled(
                value = state.sum,
                placeholder = stringResource(id = R.string.add_expense_dialog_sum_hint),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                onValueChange = viewModel::onSumChanged,
                colorScheme = ColorScheme.FINANCE
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.add_expense_dialog_receipt_title),
                    color = TamadaTheme.colors.textMain,
                    style = TamadaTheme.typography.body
                )
                Spacer(modifier = Modifier.weight(1f))
                TamadaTextWithBackground(
                    modifier = Modifier.clickable {
                        openDocument.launch(arrayOf(PDF_FORMAT))
                    },
                    textColor = if (state.receipt?.lastPathSegment.isNullOrEmpty()) {
                        TamadaTheme.colors.textHint
                    } else {
                        getPrimaryColor(scheme = ColorScheme.FINANCE)
                    },
                    text = state.receipt?.lastPathSegment
                        ?: stringResource(id = R.string.add_expense_add_receipt),
                    colorScheme = ColorScheme.FINANCE
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TamadaButton(
                colorScheme = ColorScheme.FINANCE,
                title = stringResource(id = R.string.add_expense_dialog_button),
                onClick = {
                    viewModel.onAddClick(expenseType)
                }
            )
            LaunchedEffect(state.action) {
                if (state.action == AddExpenseState.Action.BACK) onAddClick()
            }
        }
    }
}
