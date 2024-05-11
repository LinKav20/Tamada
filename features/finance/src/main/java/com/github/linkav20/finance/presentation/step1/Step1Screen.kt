package com.github.linkav20.finance.presentation.step1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.utils.OnLifecycleStart
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaDialog
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaGradientDisclaimer
import com.github.linkav20.coreui.ui.TamadaRoadMap
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.navigation.MainFinanceDestination
import com.github.linkav20.finance.navigation.MyExpensesDestination
import com.github.linkav20.finance.navigation.OnboardingDestination
import com.github.linkav20.finance.navigation.ProgressDestination
import com.github.linkav20.finance.navigation.Step1Destination
import com.github.linkav20.finance.navigation.Step2Destination
import com.github.linkav20.finance.presentation.addexpense.AddExpenseDialog
import com.github.linkav20.finance.presentation.addexpense.AddExpenseViewModel
import com.github.linkav20.finance.presentation.shared.DeadlineComponent
import com.github.linkav20.finance.presentation.shared.EditableDeadlineComponent
import com.github.linkav20.finance.presentation.shared.EditableWalletComponent
import com.github.linkav20.finance.presentation.shared.ExpenseItem
import com.github.linkav20.finance.presentation.shared.PartySumComponent
import com.github.linkav20.finance.presentation.shared.WalletComponent
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

private const val SHOW_EXPENSES = 3

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun Step1Screen(
    viewModel: Step1ViewModel,
    dialogViewModel: AddExpenseViewModel,
    navController: NavController,
    errorMapper: ErrorMapper
) {
    val scope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState().value
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    if (state.showDialog) {
        TamadaDialog(
            title = stringResource(id = R.string.dialog_title),
            body = {
                Text(
                    text = stringResource(id = R.string.dialog_subtitle_delete_next_step),
                    style = TamadaTheme.typography.caption,
                    color = TamadaTheme.colors.textMain
                )
            },
            onClose = viewModel::onCloseDialog,
            colorScheme = ColorScheme.FINANCE,
            onConfirm = {
                viewModel.onEndStep()
                navController.navigate(Step2Destination.route()) {
                    popUpTo(Step1Destination.route()) {
                        inclusive = true
                    }
                }
            }
        )
    }

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
                onAddClick = {
                    viewModel.onStart()
                    scope.launch { sheetState.hide() }
                }
            )
        },
        scrimColor = getBackgroundColor(scheme = ColorScheme.FINANCE).copy(alpha = 0.75f)
    ) {
        Content(
            state = state,
            onDeadlineChanged = viewModel::onDeadlineChanged,
            onDeadlineEditClick = viewModel::onDeadlineEditClick,
            onDeadlineSet = viewModel::onDeadlineSet,
            onEndStepClick = viewModel::onShowDialog,
            onBackClick = { navController.navigateUp() },
            onDisclaimerClick = { navController.navigate(OnboardingDestination.route()) },
            onShowProgressClick = { navController.navigate(ProgressDestination.createRoute(0)) },
            onAddExpenseClick = {
                dialogViewModel.clearState()
                scope.launch { sheetState.show() }
            },
            onMyExpensesClick = { navController.navigate(MyExpensesDestination.createRoute(0)) },
            onWalletEditClick = viewModel::onWalletEditClick,
            onSaveDataClick = viewModel::onSaveWalletData,
            onCardNumberChanged = viewModel::onCardNumberChange,
            onPhoneNumberChanged = viewModel::onPhoneNumberChange,
            onCardOwnerChanged = viewModel::onCardOwnerChange,
            onBankChanged = viewModel::onBankChange,
            onError = { throwable ->
                errorMapper.OnError(
                    throwable,
                    viewModel::onRetry,
                    ColorScheme.FINANCE
                )
            }
        )
    }

    OnLifecycleStart {
        viewModel.onStart()
    }
}

@Composable
private fun Content(
    state: Step1State,
    onBackClick: () -> Unit,
    onDisclaimerClick: () -> Unit,
    onDeadlineEditClick: () -> Unit,
    onDeadlineChanged: (OffsetDateTime) -> Unit,
    onDeadlineSet: () -> Unit,
    onShowProgressClick: () -> Unit,
    onEndStepClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onMyExpensesClick: () -> Unit,
    onWalletEditClick: () -> Unit,
    onSaveDataClick: () -> Unit,
    onCardNumberChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onCardOwnerChanged: (String) -> Unit,
    onBankChanged: (String) -> Unit,
    onError: @Composable (Throwable) -> Unit
) = Scaffold(
    modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding()
        .imePadding(),
    backgroundColor = getBackgroundColor(scheme = ColorScheme.FINANCE),
    topBar = {
        TamadaTopBar(
            title = stringResource(id = R.string.main_finance_title),
            onBackClick = onBackClick,
            colorScheme = ColorScheme.FINANCE
        )
    }
) { paddings ->
    Column(
        modifier = Modifier
            .padding(paddings)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (state.loading) {
            TamadaFullscreenLoader(scheme = ColorScheme.FINANCE)
        } else if (state.error != null) {
            onError(state.error)
        } else {
            TamadaRoadMap(
                currentIndex = 0,
                stepsCount = 3,
                onPointClick = listOf({}, {}, {}),
                titles = listOf(
                    R.string.main_finance_road_map_step_1,
                    R.string.main_finance_road_map_step_2,
                    R.string.main_finance_road_map_step_3
                ),
                colorScheme = ColorScheme.FINANCE
            )
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                TamadaGradientDisclaimer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDisclaimerClick() },
                    colorScheme = ColorScheme.FINANCE
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.step1_disclaimer_title),
                            style = TamadaTheme.typography.body,
                            color = TamadaTheme.colors.textWhite,
                            textDecoration = TextDecoration.Underline
                        )
                        Text(
                            text = stringResource(id = R.string.step1_disclaimer_subtitle),
                            style = TamadaTheme.typography.body,
                            color = TamadaTheme.colors.textWhite
                        )
                    }
                }
                if (state.isDeadlineEditable) {
                    EditableDeadlineComponent(
                        deadline = state.deadline,
                        onDeadlineChanged = onDeadlineChanged,
                        onDeadlineSet = onDeadlineSet
                    )
                } else {
                    DeadlineComponent(
                        isManager = state.isManager,
                        deadline = state.deadline,
                        onEditDeadlineClick = onDeadlineEditClick,
                        onEndStepClick = onEndStepClick
                    )
                }
                PartySumComponent(
                    sum = state.sum ?: 0.0,
                    onClick = onShowProgressClick
                )
                MyExpensesList(
                    expenses = state.expenses,
                    onAddExpenseClick = onAddExpenseClick,
                    onMyExpensesClick = onMyExpensesClick
                )
                if (state.isWalletEditable) {
                    EditableWalletComponent(
                        cartNumber = state.cardNumber,
                        phoneNumber = state.phoneNumber,
                        bank = state.bank,
                        cardOwner = state.cardOwner,
                        onCardNumberChanged = onCardNumberChanged,
                        onPhoneNumberChanged = onPhoneNumberChanged,
                        onCardOwnerChanged = onCardOwnerChanged,
                        onBankChanged = onBankChanged,
                        onSaveDataClick = onSaveDataClick
                    )
                } else {
                    WalletComponent(
                        isManager = state.isManager,
                        cartNumber = state.cardNumber,
                        phoneNumber = state.phoneNumber,
                        owner = state.cardOwner,
                        bank = state.bank,
                        onEditClick = onWalletEditClick
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun MyExpensesList(
    expenses: List<Expense>,
    onAddExpenseClick: () -> Unit,
    onMyExpensesClick: () -> Unit
) = TamadaCard(colorScheme = ColorScheme.FINANCE) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.step1_my_expenses_title),
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader
        )
        if (expenses.isEmpty()) {
            Text(
                text = stringResource(id = R.string.step1_my_expenses_subtitle),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain
            )
        } else {
            if (expenses.size < SHOW_EXPENSES) {
                expenses.forEach { ExpenseItem(it) }
            } else {
                expenses.slice(0 until SHOW_EXPENSES).forEach {
                    ExpenseItem(it)
                }
            }
        }
        if (expenses.size > SHOW_EXPENSES) {
            Text(
                text = pluralStringResource(
                    R.plurals.step1_my_expenses_show_more_items,
                    count = expenses.size - SHOW_EXPENSES,
                    expenses.size - SHOW_EXPENSES
                ),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain,
            )
        }
        TamadaButton(
            title = stringResource(id = R.string.step1_my_expenses_open_full),
            type = ButtonType.SECONDARY,
            colorScheme = ColorScheme.FINANCE,
            onClick = onMyExpensesClick
        )
        TamadaButton(
            title = stringResource(id = R.string.step1_my_expenses_add_expense),
            colorScheme = ColorScheme.FINANCE,
            onClick = onAddExpenseClick
        )
    }
}
