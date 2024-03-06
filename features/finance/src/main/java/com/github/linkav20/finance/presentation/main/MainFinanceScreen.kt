package com.github.linkav20.finance.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
import com.github.linkav20.finance.presentation.dialog.TurnOnFinanceDialog

@Composable
fun MainFinanceScreen(
    viewModel: MainFinanceViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        backgroundColor = getBackgroundColor(scheme = ColorScheme.FINANCE),
        topBar = {
            TamadaTopBar(
                colorScheme = ColorScheme.FINANCE,
                title = stringResource(id = R.string.main_finance_title),
                onBackClick = { navController.navigateUp() },
            )
        },
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            when (state) {
                is MainFinanceState.BasicState -> {
                    BasicContent(
                        state = state,
                        onClose = viewModel::onCloseDialog,
                        onConfirm = viewModel::onConfirmDialog,
                        onOpen = viewModel::onOpenDialog
                    )
                }

                is MainFinanceState.Error -> {}
                MainFinanceState.Loading -> TamadaFullscreenLoader(scheme = ColorScheme.FINANCE)
            }
            //ExpensesContent()
        }
    }
}

@Composable
private fun BasicContent(
    state: MainFinanceState.BasicState,
    onClose: () -> Unit,
    onConfirm: () -> Unit,
    onOpen: () -> Unit
) = Column(
    modifier = Modifier.padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    if (state.showDialog) {
        TurnOnFinanceDialog(
            onClose = onClose,
            onConfirm = onConfirm
        )
    }
    TamadaCard(colorScheme = ColorScheme.FINANCE) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.basic_finance_onbording_title),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.basic_finance_onbording_subtitle),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain
            )
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                modifier = Modifier
                    .height(192.dp)
                    .clip(TamadaTheme.shapes.mediumSmall)
                    .fillMaxWidth(),
                painter = painterResource(id = com.github.linkav20.coreui.R.drawable.avatar),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))
            TamadaButton(
                title = stringResource(id = R.string.basic_finance_onbording_button),
                type = ButtonType.SECONDARY,
                colorScheme = ColorScheme.FINANCE,
                onClick = {}
            )
        }
    }
    TamadaCard(colorScheme = ColorScheme.FINANCE) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.basic_finance_turn_off_title),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader
            )
            if (state.isManager) {
                Spacer(modifier = Modifier.height(24.dp))
                TamadaButton(
                    title = stringResource(id = R.string.basic_finance_turn_on_button),
                    colorScheme = ColorScheme.FINANCE,
                    onClick = onOpen
                )
            }
        }
    }
}

@Composable
private fun ExpensesContent(
) = Column(
    modifier = Modifier.padding(24.dp),
) {
    TamadaRoadMap(
        colorScheme = ColorScheme.FINANCE,
        currentIndex = 0,
        stepsCount = 3,
        onPointClick = listOf({}, {}, {}),
        titles = listOf(
            R.string.main_finance_road_map_step_1,
            R.string.main_finance_road_map_step_2,
            R.string.main_finance_road_map_step_3,
        ),
    )
}