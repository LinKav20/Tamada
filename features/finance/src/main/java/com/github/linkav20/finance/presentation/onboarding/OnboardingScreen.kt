package com.github.linkav20.finance.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.SegmentElement
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaSegmentButton
import com.github.linkav20.coreui.ui.TamadaSwitchButton
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.finance.R

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

    Content(
        step = state.step,
        onNextClick = viewModel::onNextClick,
        onPrevClick = viewModel::onPrevClick,
        onBackClick = { navController.navigateUp() }
    )

    LaunchedEffect(state.action) {
        if (state.action == OnboardingState.Action.BACK) {
            navController.navigateUp()
        }
    }
}

@Composable
private fun Content(
    step: OnboardingState.Step,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    onBackClick: () -> Unit
) = Scaffold(
    backgroundColor = getBackgroundColor(scheme = ColorScheme.FINANCE),
    topBar = {
        TamadaTopBar(
            colorScheme = ColorScheme.FINANCE,
            title = stringResource(id = R.string.onboarding_title),
            onBackClick = onBackClick
        )
    },
) { paddings ->
    Column(
        modifier = Modifier
            .padding(paddings)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        when (step) {
            OnboardingState.Step.START -> {
                CardComponent(
                    title = stringResource(id = R.string.onboarding_start_title),
                    subtitle = stringResource(id = R.string.onboarding_start_subtitle),
                    painter = painterResource(id = R.drawable.onboarding_step_start),
                    imageHeight = 300.dp
                )
            }

            OnboardingState.Step.STEP1 -> {
                CardComponent(
                    title = stringResource(id = R.string.onboarding_step1_title),
                    subtitle = stringResource(id = R.string.onboarding_step1_subtitle),
                    painter = painterResource(id = R.drawable.onboarding_step_1)
                )
            }

            OnboardingState.Step.STEP2 -> {
                CardComponent(
                    title = stringResource(id = R.string.onboarding_step2_title),
                    subtitle = stringResource(id = R.string.onboarding_step2_subtitle),
                    painter = painterResource(id = R.drawable.onboarding_step2_2)
                )
            }

            OnboardingState.Step.STEP3 -> {
                CardComponent(
                    title = stringResource(id = R.string.onboarding_step3_title),
                    subtitle = stringResource(id = R.string.onboarding_step3_subtitle),
                    painter = painterResource(id = R.drawable.onboarding_step_3)
                )
            }

            OnboardingState.Step.FINISH -> {
                CardComponent(
                    title = stringResource(id = R.string.onboarding_finish_title),
                    painter = painterResource(id = R.drawable.onboarding_start),
                    imageHeight = 300.dp
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        TamadaCard(
            colorScheme = ColorScheme.FINANCE
        ) {
            TamadaSegmentButton(
                colorScheme = ColorScheme.FINANCE,
                elements = listOf(
                    SegmentElement.SegmentButton(
                        title = stringResource(id = R.string.onboarding_back_button),
                        //  isEnabled = state.isPreviousStep,
                        onClick = onPrevClick,
                    ),
                    SegmentElement.Divider(),
                    SegmentElement.SegmentButton(
                        title = stringResource(id = R.string.onboarding_next_button),
                        isPrimary = true,
                        onClick = onNextClick,
                    ),
                ),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun CardComponent(
    title: String,
    subtitle: String? = null,
    painter: Painter,
    imageHeight: Dp = 200.dp
) = Column {
    TamadaCard(
        colorScheme = ColorScheme.FINANCE
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader,
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subtitle,
                    style = TamadaTheme.typography.caption,
                    color = TamadaTheme.colors.textMain,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                modifier = Modifier
                    .height(imageHeight)
                    .clip(TamadaTheme.shapes.mediumSmall)
                    .fillMaxWidth(),
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
    }
}
