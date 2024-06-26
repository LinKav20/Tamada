package com.github.linkav20.finance.presentation.step3

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaRoadMap
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.finance.R
import com.github.linkav20.finance.navigation.MyExpensesDestination
import com.github.linkav20.finance.navigation.ProgressDestination
import com.github.linkav20.finance.presentation.shared.PartySumComponent
import com.github.linkav20.finance.presentation.shared.WalletComponent

@Composable
fun Step3Screen(
    viewModel: Step3ViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

    Content(
        state = state,
        onBackClick = { navController.navigateUp() },
        onMyExpensesClick = { navController.navigate(MyExpensesDestination.createRoute(2)) },
        onShowProgressClick = { navController.navigate(ProgressDestination.createRoute(2)) },
        onDeptDoneClick = viewModel::onDeptDoneClick
    )
}

@Composable
private fun Content(
    state: Step3State,
    onBackClick: () -> Unit,
    onMyExpensesClick: () -> Unit,
    onShowProgressClick: () -> Unit,
    onDeptDoneClick: () -> Unit
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
            currentIndex = 2,
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
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
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
                                stringResource(id = R.string.step3_dept_title)
                            } else {
                                stringResource(id = R.string.step3_dept_title_done)
                            },
                            style = TamadaTheme.typography.head,
                            color = TamadaTheme.colors.textHeader
                        )
                        Text(
                            text = stringResource(
                                id = R.string.step1_sum_formatter,
                                state.dept ?: 0.0
                            ),
                            style = TamadaTheme.typography.body,
                            color = TamadaTheme.colors.textMain,
                        )
                    }
                    Text(
                        text = if (!state.isDept) {
                            stringResource(id = R.string.step3_dept_subtitle)
                        } else {
                            stringResource(id = R.string.step3_dept_done_subtitle)
                        },
                        style = TamadaTheme.typography.caption,
                        color = TamadaTheme.colors.textMain
                    )
                    TamadaButton(
                        title = stringResource(id = R.string.step3_dept_button),
                        type = if (state.isDept) {
                            ButtonType.SECONDARY
                        } else {
                            ButtonType.PRIMARY
                        },
                        colorScheme = ColorScheme.FINANCE,
                        onClick = onDeptDoneClick
                    )
                }
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
            MyExpenses(
                total = state.myTotal ?: 0.0,
                onMyExpensesClick = onMyExpensesClick
            )
            Spacer(modifier = Modifier.height(16.dp))
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
