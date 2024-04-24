package com.github.linkav20.finance.presentation.progress

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaGradientDisclaimer
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getUserAvatar
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.UserUI
import com.github.linkav20.finance.presentation.shared.ExpandableExpensesCard
import com.github.linkav20.finance.presentation.shared.ExpenseItem

@Composable
fun ProgressScreen(
    viewModel: ProgressViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

    Content(
        state = state,
        onExpandUserExpenseClick = viewModel::onExpandUserExpenseClick,
        onBackClick = { navController.navigateUp() }
    )
}

@Composable
private fun Content(
    state: ProgressState,
    onExpandUserExpenseClick: (UserUI) -> Unit,
    onBackClick: () -> Unit
) = Scaffold(
    modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding()
        .imePadding(),
    backgroundColor = getBackgroundColor(scheme = ColorScheme.FINANCE),
    topBar = {
        TamadaTopBar(
            title = stringResource(id = R.string.progress_title),
            onBackClick = onBackClick,
            colorScheme = ColorScheme.FINANCE
        )
    }
) { paddings ->
    Box(modifier = Modifier.padding(paddings)) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            if (state.total != null) {
                TamadaGradientDisclaimer(
                    colorScheme = ColorScheme.FINANCE
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.total.name,
                            style = TamadaTheme.typography.body,
                            color = TamadaTheme.colors.textWhite
                        )
                        Spacer(
                            Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                        Text(
                            text = stringResource(R.string.step1_sum_formatter, state.total.sum),
                            style = TamadaTheme.typography.body,
                            color = TamadaTheme.colors.textWhite
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
            TamadaCard(colorScheme = ColorScheme.FINANCE) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = when (state.step) {
                            0 -> stringResource(id = R.string.progress_users_done_title_step1)
                            1 -> stringResource(id = R.string.progress_users_done_title_step2)
                            else -> stringResource(id = R.string.progress_users_done_title_step3)
                        },
                        style = TamadaTheme.typography.head,
                        color = TamadaTheme.colors.textHeader
                    )
                    if (state.getUsersDone.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.progress_empty_list_done_users),
                            style = TamadaTheme.typography.body,
                            color = TamadaTheme.colors.textMain
                        )
                    } else {
                        DoneUsers(
                            step = state.step,
                            users = state.getUsersDone,
                            isManager = state.isManager,
                            onExpandUserExpenseClick = onExpandUserExpenseClick
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            TamadaCard(colorScheme = ColorScheme.FINANCE) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = when (state.step) {
                            0 -> stringResource(id = R.string.progress_users_not_done_title_step1)
                            1 -> stringResource(id = R.string.progress_users_not_done_title_step2)
                            else -> stringResource(id = R.string.progress_users_not_done_title_step3)
                        },
                        style = TamadaTheme.typography.head,
                        color = TamadaTheme.colors.textHeader
                    )
                    if (state.getUsersNotDone.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.progress_empty_list_not_done_users),
                            style = TamadaTheme.typography.body,
                            color = TamadaTheme.colors.textMain
                        )
                    } else {
                        NotDoneUsers(
                            isManager = state.isManager,
                            step = state.step,
                            users = state.getUsersNotDone,
                            onExpandUserExpenseClick = onExpandUserExpenseClick
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    if (state.loading) TamadaFullscreenLoader(scheme = ColorScheme.FINANCE)
}

@Composable
private fun NotDoneUsers(
    isManager: Boolean,
    step: Int,
    users: List<UserUI>,
    onExpandUserExpenseClick: (UserUI) -> Unit
) {
    users.forEach {
        if (step != 0) {
            ExpandableExpensesCard(
                user = it,
                isManager = isManager,
                isAccent = false,
                step = 0,
                onExpand = onExpandUserExpenseClick,
                onErrorInExpensesClick = {},
                onReceiptClick = {},
                onMyExpensesClick = {},
                onSpecificButtonClick = {}
            )
        } else {
            TamadaCard(colorScheme = ColorScheme.FINANCE) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = getUserAvatar(it.avatar),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = it.name,
                        style = TamadaTheme.typography.head,
                        color = getPrimaryColor(scheme = ColorScheme.FINANCE),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun DoneUsers(
    step: Int,
    isManager: Boolean,
    users: List<UserUI>,
    onExpandUserExpenseClick: (UserUI) -> Unit
) = Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    users.forEach {
        ExpandableExpensesCard(
            isAccent = step != 0,
            step = step,
            isManager = isManager,
            user = it,
            onExpand = onExpandUserExpenseClick,
            onErrorInExpensesClick = {},
            onReceiptClick = {},
            onMyExpensesClick = {},
            onSpecificButtonClick = {}
        )
    }
}