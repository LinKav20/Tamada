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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.finance.R
import com.github.linkav20.finance.navigation.MainFinanceDestination
import com.github.linkav20.finance.navigation.OnboardingDestination
import com.github.linkav20.finance.navigation.Step1Destination
import com.github.linkav20.finance.navigation.Step2Destination
import com.github.linkav20.finance.navigation.Step3Destination
import com.github.linkav20.finance.presentation.dialog.Dialog

@Composable
fun MainFinanceScreen(
    viewModel: MainFinanceViewModel,
    navController: NavController,
    errorMapper: ErrorMapper
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
            if (state.loading) {
                TamadaFullscreenLoader(scheme = ColorScheme.FINANCE)
            } else if (state.error != null) {
                errorMapper.OnError(
                    throwable = state.error,
                    onActionClick = viewModel::onRetry,
                    colorScheme = ColorScheme.FINANCE
                )
            } else {
                BasicContent(
                    state = state,
                    onCloseDialog = viewModel::onCloseDialog,
                    onConfirm = viewModel::onConfirmDialog,
                    onOpenDialog = viewModel::onOpenDialog,
                    onOnboardingClick = { navController.navigate(OnboardingDestination.route()) }
                )
            }
        }
    }

    LaunchedEffect(state.tab) {
        when (state.tab) {
            MainFinanceState.Tab.STEP1 -> navController.navigate(Step1Destination.route()) {
                popUpTo(MainFinanceDestination.route()) {
                    inclusive = true
                }
            }

            MainFinanceState.Tab.STEP2 -> navController.navigate(Step2Destination.route()) {
                popUpTo(MainFinanceDestination.route()) {
                    inclusive = true
                }
            }

            MainFinanceState.Tab.STEP3 -> navController.navigate(Step3Destination.route()) {
                popUpTo(MainFinanceDestination.route()) {
                    inclusive = true
                }
            }

            else -> {}
        }
    }
}

@Composable
private fun BasicContent(
    state: MainFinanceState,
    onCloseDialog: () -> Unit,
    onConfirm: () -> Unit,
    onOpenDialog: () -> Unit,
    onOnboardingClick: () -> Unit
) = Column(
    modifier = Modifier.padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    if (state.showDialog) {
        Dialog(
            subtitle = stringResource(id = R.string.dialog_subtitle_turn_on_finance),
            onClose = onCloseDialog,
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
                    .height(264.dp)
                    .clip(TamadaTheme.shapes.mediumSmall)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.onboarding_start),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(24.dp))
            TamadaButton(
                title = stringResource(id = R.string.basic_finance_onbording_button),
                type = ButtonType.SECONDARY,
                colorScheme = ColorScheme.FINANCE,
                onClick = onOnboardingClick
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
                    onClick = onOpenDialog
                )
            }
        }
    }
}
