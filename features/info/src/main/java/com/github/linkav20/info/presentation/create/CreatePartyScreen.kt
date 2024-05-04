package com.github.linkav20.info.presentation.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.SegmentElement
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaRoadMap
import com.github.linkav20.coreui.ui.TamadaSegmentButton
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.info.R
import java.time.OffsetDateTime

@Composable
fun CreatePartyScreen(
    viewModel: CreatePartyViewModel,
    navController: NavController,
) {
    val state = viewModel.state.collectAsState().value
    Content(
        state = state,
        roadMapClicks = viewModel.onRoadMapClicks(),
        onNextClick = viewModel::onNextClick,
        onPrevClick = viewModel::onPrevClick,
        onExpensesButtonClick = viewModel::onExpensesButtonClick,
        onNameChanged = viewModel::onNameChanged,
        onStartDateChanged = viewModel::onStartDateChanged,
        onEndDateChanged = viewModel::onEndDateChanged,
        onAddressChanged = viewModel::onAddressChanged,
        onAddressAdditionalChanged = viewModel::onAddressAdditionalChanged,
        onImportantChanged = viewModel::onImportantChanged,
        onThemeChanged = viewModel::onThemeChanged,
        onMoodboardinkChanged = viewModel::onMoodboardinkChanged,
        onDressCodeChanged = viewModel::onDressCodeChanged,
        onAddressLinkChanged = viewModel::onAddressLinkChanged,
        onBackClick = { navController.navigateUp() },
    )

    LaunchedEffect(state.action) {
        when (state.action) {
            CreationPartyState.Action.BACK -> navController.navigateUp()
            else -> {}
        }
        viewModel.nullifyAction()
    }
}

@Composable
private fun Content(
    state: CreationPartyState,
    onExpensesButtonClick: () -> Unit,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    roadMapClicks: List<() -> Unit>,
    onNameChanged: (String) -> Unit,
    onStartDateChanged: (OffsetDateTime) -> Unit,
    onEndDateChanged: (OffsetDateTime) -> Unit,
    onBackClick: () -> Unit,
    onAddressChanged: (String) -> Unit,
    onImportantChanged: (String) -> Unit,
    onAddressAdditionalChanged: (String) -> Unit,
    onThemeChanged: (String) -> Unit,
    onMoodboardinkChanged: (String) -> Unit,
    onDressCodeChanged: (String) -> Unit,
    onAddressLinkChanged: (String) -> Unit
) = Scaffold(
    modifier =
    Modifier
        .statusBarsPadding()
        .navigationBarsPadding()
        .imePadding(),
    backgroundColor = getBackgroundColor(scheme = ColorScheme.MAIN),
    topBar = {
        TamadaTopBar(
            title = stringResource(id = R.string.creation_party_title),
            onBackClick = onBackClick,
        )
    },
) { paddings ->
    Column(
        modifier =
        Modifier
            .padding(paddings)
            .padding(24.dp),
    ) {
        TamadaRoadMap(
            currentIndex = state.tab.index,
            stepsCount = state.tabsCount,
            onPointClick = roadMapClicks,
            titles =
            listOf(
                R.string.creation_party_screen_main_road_map,
                R.string.creation_party_screen_address_road_map,
                R.string.creation_party_screen_important_road_map,
                R.string.creation_party_screen_theme_road_map,
            ),
        )
        if (state.loading) {
            TamadaFullscreenLoader()
        } else {
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
                item {
                    when (state.tab.index) {
                        CreationPartyState.Tab.MAIN.index ->
                            MainContent(
                                state = state,
                                onExpensesButtonClick = onExpensesButtonClick,
                                onStartDateChanged = onStartDateChanged,
                                onNameChanged = onNameChanged,
                                onEndDateChanged = onEndDateChanged,
                            )

                        CreationPartyState.Tab.ADDRESS.index ->
                            AddressContent(
                                state = state,
                                onAddressChanged = onAddressChanged,
                                onAddressAdditionChanged = onAddressAdditionalChanged,
                                onAddressLinkChanged = onAddressLinkChanged
                            )

                        CreationPartyState.Tab.IMPORTANT.index ->
                            ImportantContent(
                                state = state,
                                onImportantChanged = onImportantChanged,
                            )

                        CreationPartyState.Tab.THEME.index ->
                            ThemeContent(
                                state = state,
                                onThemeChanged = onThemeChanged,
                                onDressCodeChanged = onDressCodeChanged,
                                onMoodboardinkChanged = onMoodboardinkChanged,
                            )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                }
                item {
                    TamadaCard {
                        TamadaSegmentButton(
                            elements =
                            listOf(
                                SegmentElement.SegmentButton(
                                    title =
                                    if (!state.isPreviousStep) {
                                        null
                                    } else {
                                        stringResource(
                                            id = R.string.creation_party_screen_prev_button,
                                        )
                                    },
                                    isEnabled = state.isPreviousStep,
                                    onClick = onPrevClick,
                                ),
                                SegmentElement.Divider(),
                                SegmentElement.SegmentButton(
                                    title = stringResource(id = R.string.creation_party_screen_next_button),
                                    isPrimary = true,
                                    onClick = onNextClick,
                                ),
                            ),
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    TamadaTheme {
        Content(
            state =
            CreationPartyState(
                tab = CreationPartyState.Tab.MAIN,
            ),
            roadMapClicks = listOf({}),
            onExpensesButtonClick = {},
            onNextClick = {},
            onPrevClick = {},
            onBackClick = {},
            onStartDateChanged = {},
            onEndDateChanged = {},
            onNameChanged = {},
            onAddressAdditionalChanged = {},
            onAddressChanged = {},
            onImportantChanged = {},
            onThemeChanged = {},
            onMoodboardinkChanged = {},
            onDressCodeChanged = {},
            onAddressLinkChanged = {}
        )
    }
}
