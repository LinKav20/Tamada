package com.github.linkav20.finance.presentation.step2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaRoadMap
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.navigation.MyExpensesDestination
import com.github.linkav20.finance.navigation.ProgressDestination
import com.github.linkav20.finance.navigation.Step2Destination
import com.github.linkav20.finance.navigation.Step3Destination
import com.github.linkav20.finance.presentation.addexpense.AddExpenseDialog
import com.github.linkav20.finance.presentation.addexpense.AddExpenseViewModel
import com.github.linkav20.finance.presentation.dialog.Dialog
import com.github.linkav20.finance.presentation.shared.DeadlineComponent
import com.github.linkav20.finance.presentation.shared.EditableDeadlineComponent
import com.github.linkav20.finance.presentation.shared.EditableWalletComponent
import com.github.linkav20.finance.presentation.shared.PartySumComponent
import com.github.linkav20.finance.presentation.shared.WalletComponent
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import kotlin.math.abs

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Step2Screen(
    viewModel: Step2ViewModel,
    dialogViewModel: AddExpenseViewModel,
    navController: NavController,
    errorMapper: ErrorMapper
) {
    val scope = rememberCoroutineScope()
    val state = viewModel.state.collectAsState().value
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    if (state.showDialog) {
        Dialog(
            subtitle = stringResource(id = R.string.dialog_subtitle_delete_next_step),
            onClose = viewModel::onCloseDialog,
            onConfirm = {
                viewModel.onEndStep()
                navController.navigate(Step3Destination.route()) {
                    popUpTo(Step2Destination.route()) {
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
                expenseType = Expense.Type.DEBT,
                onAddClick = {
                    viewModel.onAddClick()
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
            onShowProgressClick = { navController.navigate(ProgressDestination.createRoute(1)) },
            onAddExpenseClick = {
                dialogViewModel.clearState()
                scope.launch { sheetState.show() }
            },
            onMyExpensesClick = { navController.navigate(MyExpensesDestination.createRoute(1)) },
            onWalletEditClick = viewModel::onWalletEditClick,
            onSaveDataClick = viewModel::onSaveWalletData,
            onCardNumberChanged = viewModel::onCardNumberChange,
            onPhoneNumberChanged = viewModel::onPhoneNumberChange,
            onCardOwnerChanged = viewModel::onCardOwnerChange,
            onBankChanged = viewModel::onBankChange,
            onError = {
                errorMapper.OnError(
                    throwable = it,
                    onActionClick = viewModel::onRetry,
                    colorScheme = ColorScheme.FINANCE
                )
            }
        )
    }
}

@Composable
private fun Content(
    state: Step2State,
    onBackClick: () -> Unit,
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
        TamadaRoadMap(
            currentIndex = 1,
            stepsCount = 3,
            onPointClick = listOf({}, {}, {}),
            titles = listOf(
                R.string.main_finance_road_map_step_1,
                R.string.main_finance_road_map_step_2,
                R.string.main_finance_road_map_step_3
            ),
            colorScheme = ColorScheme.FINANCE
        )
        if (state.loading) {
            TamadaFullscreenLoader(scheme = ColorScheme.FINANCE)
        } else if (state.error != null) {
            onError(state.error)
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
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
                    subtitle = stringResource(
                        R.string.step2_total_sum_subtitle,
                        state.calculation?.forPerson ?: "",
                        state.calculation?.personDept ?: ""
                    ),
                    onClick = onShowProgressClick
                )
                TamadaCard(colorScheme = ColorScheme.FINANCE) {
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
                                text = if (!state.isDept) {
                                    stringResource(id = R.string.step2_dept_title_dept)
                                } else {
                                    if (state.dept != null && state.dept > 0) {
                                        stringResource(id = R.string.step2_dept_title_done)
                                    } else {
                                        stringResource(id = R.string.step2_dept_title_not_dept)
                                    }
                                },
                                style = TamadaTheme.typography.head,
                                color = TamadaTheme.colors.textHeader
                            )
                            Text(
                                text = stringResource(
                                    id = R.string.step1_sum_formatter,
                                    state.dept?.let { abs(it) } ?: 0.0
                                ),
                                style = TamadaTheme.typography.body,
                                color = TamadaTheme.colors.textMain,
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.step2_dept_subtitle),
                            style = TamadaTheme.typography.caption,
                            color = TamadaTheme.colors.textMain
                        )
                        TamadaButton(
                            title = if (state.dept != null && state.dept > 0) {
                                stringResource(id = R.string.step2_dept_button)
                            } else {
                                stringResource(id = R.string.step2_dept_button_no_dept)
                            },
                            type = if ((state.dept != null && state.dept <= 0) || state.isDept) {
                                ButtonType.SECONDARY
                            } else {
                                ButtonType.PRIMARY
                            },
                            colorScheme = ColorScheme.FINANCE,
                            onClick = if (!state.isDept) {
                                onAddExpenseClick
                            } else {
                                {}
                            }
                        )
                    }
                }
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
                MyExpenses(
                    total = state.myTotal ?: 0.0,
                    onMyExpensesClick = onMyExpensesClick
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
private fun MyExpenses(
    total: Double,
    onMyExpensesClick: () -> Unit
) = TamadaCard(colorScheme = ColorScheme.FINANCE) {
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
                text = stringResource(id = R.string.step1_my_expenses_title),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader
            )
            Text(
                text = stringResource(
                    id = R.string.step1_sum_formatter,
                    total
                ),
                style = TamadaTheme.typography.body,
                color = TamadaTheme.colors.textMain,
            )
        }
        TamadaButton(
            title = stringResource(id = R.string.step1_my_expenses_open_full),
            type = ButtonType.SECONDARY,
            colorScheme = ColorScheme.FINANCE,
            onClick = onMyExpensesClick
        )
    }
}

