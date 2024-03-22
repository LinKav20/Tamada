package com.github.linkav20.finance.presentation.myexpenses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.core.utils.printPdf
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaTextWithBackground
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.R as CoreR
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.presentation.addexpense.AddExpenseDialog
import com.github.linkav20.finance.presentation.addexpense.AddExpenseViewModel
import com.github.linkav20.finance.presentation.dialog.Dialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyExpensesScreen(
    viewModel: MyExpensesViewModel,
    dialogViewModel: AddExpenseViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = TamadaTheme.shapes.large.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        ),
        sheetBackgroundColor = TamadaTheme.colors.backgroundPrimary,
        sheetContentColor = TamadaTheme.colors.backgroundPrimary,
        sheetContent = {
            AddExpenseDialog(
                viewModel = dialogViewModel,
                expense = state.selectExpense,
                expenseType = state.selectExpense?.type ?: Expense.Type.SUM,
                onAddClick = { scope.launch { sheetState.hide() } }
            )
        },
        scrimColor = getBackgroundColor(scheme = ColorScheme.FINANCE).copy(alpha = 0.75f)
    ) {
        if (state.showDialog) {
            Dialog(
                subtitle = stringResource(id = R.string.dialog_subtitle_delete_expense),
                onClose = viewModel::onCloseDialogClick,
                onConfirm = viewModel::onDeleteExpenseClick
            )
        }
        Content(
            expenses = state.expenses,
            onBackClick = { navController.navigateUp() },
            onSelectExpense = viewModel::onSelectExpense,
            onReceiptClick = viewModel::onReceiptClick,
            onDeleteButtonClick = viewModel::onDeleteButtonClick,
            onAddExpenseClick = {
                dialogViewModel.clearState()
                scope.launch { sheetState.show() }
            }
        )
    }

    val context = LocalContext.current
    LaunchedEffect(state.uri) {
        if (state.uri != null) {
            printPdf(context, state.uri)
            viewModel.nullifyUri()
        }
    }
}

@Composable
private fun Content(
    expenses: List<Expense>,
    onBackClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onReceiptClick: (Expense) -> Unit,
    onSelectExpense: (Expense?) -> Unit
) = Scaffold(
    backgroundColor = getBackgroundColor(scheme = ColorScheme.FINANCE),
    topBar = {
        TamadaTopBar(
            colorScheme = ColorScheme.FINANCE,
            title = stringResource(id = R.string.my_expenses_title),
            onBackClick = onBackClick
        )
    },
    bottomBar = {
        TamadaCard(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            colorScheme = ColorScheme.FINANCE
        ) {
            TamadaButton(
                title = stringResource(id = R.string.my_expenses_add_button),
                colorScheme = ColorScheme.FINANCE,
                onClick = {
                    onSelectExpense(null)
                    onAddExpenseClick()
                }
            )
        }
    }
) {
    LazyColumn(
        modifier = Modifier
            .padding(it)
            .padding(24.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }
        items(expenses) {
            ExpenseItem(
                expense = it,
                onDeleteButtonClick = onDeleteButtonClick,
                onReceiptClick = onReceiptClick,
                onEditExpenseClick = {
                    onSelectExpense(it)
                    onAddExpenseClick()
                }
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
private fun ExpenseItem(
    expense: Expense,
    onEditExpenseClick: () -> Unit,
    onReceiptClick: (Expense) -> Unit,
    onDeleteButtonClick: () -> Unit
) = TamadaCard(
    modifier = Modifier.padding(vertical = 8.dp),
    colorScheme = ColorScheme.FINANCE
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier,
                text = expense.name,
                color = TamadaTheme.colors.textMain,
                style = TamadaTheme.typography.head
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier,
                text = stringResource(
                    id = R.string.my_expenses_sum_formatter,
                    expense.sum
                ),
                color = TamadaTheme.colors.textHeader,
                style = TamadaTheme.typography.head
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.my_expenses_receipt_title),
                color = TamadaTheme.colors.textMain,
                style = TamadaTheme.typography.body
            )
            Spacer(modifier = Modifier.weight(1f))
            TamadaTextWithBackground(
                modifier = Modifier.clickable {
                    if (expense.uri?.lastPathSegment.isNullOrEmpty()) {
                        onReceiptClick(expense)
                    }
                },
                textColor = if (expense.uri?.lastPathSegment.isNullOrEmpty()) {
                    TamadaTheme.colors.textHint
                } else {
                    getPrimaryColor(scheme = ColorScheme.FINANCE)
                },
                text = expense.uri?.lastPathSegment
                    ?: stringResource(id = R.string.my_expenses_receipt_value),
                colorScheme = ColorScheme.FINANCE
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            TamadaButton(
                colorScheme = ColorScheme.FINANCE,
                iconPainter = painterResource(id = CoreR.drawable.delete_icon),
                backgroundColor = TamadaTheme.colors.textWhite,
                iconColor = TamadaTheme.colors.statusNegative,
                elevation = 20.dp,
                onClick = onDeleteButtonClick
            )
            Spacer(modifier = Modifier.width(16.dp))
            TamadaButton(
                colorScheme = ColorScheme.FINANCE,
                type = ButtonType.SECONDARY,
                backgroundColor = TamadaTheme.colors.textWhite,
                title = stringResource(id = R.string.my_expenses_edit_button),
                elevation = 20.dp,
                onClick = onEditExpenseClick
            )
        }
    }
}
