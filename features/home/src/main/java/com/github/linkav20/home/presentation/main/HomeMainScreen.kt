package com.github.linkav20.home.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.auth.navigation.AuthGraphDestination
import com.github.linkav20.core.navigation.common.CreatePartyDestination
import com.github.linkav20.core.navigation.common.InfoTabDestination
import com.github.linkav20.core.utils.OnLifecycleStart
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.DebounceIconButton
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getUserAvatar
import com.github.linkav20.home.R
import com.github.linkav20.home.domain.model.Party
import com.github.linkav20.home.navigation.LkDestination
import com.github.linkav20.coreui.R as CoreR

@Composable
fun HomeMainScreen(
    viewModel: HomeMainViewModel,
    navController: NavController,
) {
    val state = viewModel.state.collectAsState().value

    if (state.loading) {
        TamadaFullscreenLoader()
    } else {
        Content(
            state = state,
            guestParties = state.guestParties,
            manageParties = state.managerParties,
            onPartyClick = viewModel::onPartyClick,
            onAddPartyClick = { navController.navigate(CreatePartyDestination.route()) },
            onLkClick = { navController.navigate(LkDestination.route()) },
            onLogoutClick = viewModel::onLogoutClick
        )
    }

    LaunchedEffect(state.action) {
        val action = state.action
        when (action) {
            HomeMainScreenState.Action.PARTY -> {
                navController.navigate(InfoTabDestination.route())
                viewModel.nullifyAction()
            }

            HomeMainScreenState.Action.AUTH -> {
                navController.navigate(AuthGraphDestination.route())
                viewModel.nullifyAction()
            }

            else -> {}
        }
    }

    OnLifecycleStart {
        viewModel.getData()
    }
}

@Composable
private fun Content(
    state: HomeMainScreenState,
    guestParties: List<Party>,
    manageParties: List<Party>,
    onAddPartyClick: () -> Unit,
    onPartyClick: (Party) -> Unit,
    onLkClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        backgroundColor = TamadaTheme.colors.backgroundPrimary,
        topBar = {
            TamadaTopBar(
                transparent = true,
                actions = {
                    DebounceIconButton(onLogoutClick) {
                        Icon(
                            painterResource(CoreR.drawable.baseline_logout_24),
                            contentDescription = null,
                            tint = TamadaTheme.colors.textMain,
                        )
                    }
                }
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dp),
        ) {
            Spacer(modifier = Modifier.height(38.dp))
            Box(
                modifier = Modifier.clickable { onLkClick() },
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = getUserAvatar(state.userAvatar),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(64.dp)
                        .clip(CircleShape),
                )
                if (!state.isWalletFilled) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(getPrimaryColor(scheme = ColorScheme.MAIN)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "!",
                            color = TamadaTheme.colors.statusWarning,
                            style = TamadaTheme.typography.head,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.home_main_screen_hello_message, state.userName),
                color = TamadaTheme.colors.textHeader,
                style = TamadaTheme.typography.head,
            )
            Spacer(modifier = Modifier.height(38.dp))
            Text(
                text = stringResource(id = R.string.home_main_screen_info_message),
                color = TamadaTheme.colors.textMain,
                style = TamadaTheme.typography.body,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(24.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(if (manageParties.isEmpty() && guestParties.isEmpty()) 1 else 2),
            ) {
                item {
                    Text(
                        modifier =
                        Modifier
                            .align(alignment = Alignment.Start)
                            .padding(horizontal = 8.dp),
                        text = stringResource(R.string.home_main_screen_manage_party_title),
                        color = TamadaTheme.colors.textHeader,
                        style = TamadaTheme.typography.head,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item { Box {} }
                item {
                    BoxWithConstraints {
                        val maxWidth = maxWidth
                        Box(
                            modifier =
                            Modifier
                                .height(if (manageParties.isEmpty()) maxWidth / 2 else maxWidth)
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(
                                    TamadaTheme.colors.textCaption,
                                    TamadaTheme.shapes.mediumSmall,
                                )
                                .clickable { onAddPartyClick() },
                        ) {
                            Icon(
                                modifier = Modifier.align(Alignment.Center),
                                painter = painterResource(id = CoreR.drawable.outline_add_24),
                                tint = TamadaTheme.colors.textHeader,
                                contentDescription = null,
                            )
                        }
                    }
                }
                items(manageParties) {
                    Party(
                        party = it,
                        onPartyClick = onPartyClick,
                    )
                }
                if (manageParties.size % 2 == 0) {
                    item { Box {} }
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .padding(horizontal = 8.dp),
                        text = stringResource(R.string.home_main_screen_guest_party_title),
                        color = TamadaTheme.colors.textHeader,
                        style = TamadaTheme.typography.head,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item { Box {} }
                if (guestParties.isEmpty()) {
                    item {
                        Text(
                            modifier = Modifier
                                .align(alignment = Alignment.Start)
                                .padding(horizontal = 8.dp),
                            text = stringResource(id = R.string.home_main_screen_guests_empty),
                            color = TamadaTheme.colors.textMain,
                            style = TamadaTheme.typography.body
                        )
                    }
                } else {
                    items(guestParties) {
                        Party(
                            party = it,
                            onPartyClick = onPartyClick,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Party(
    party: Party,
    onPartyClick: (Party) -> Unit,
) = BoxWithConstraints {
    val maxWidth = maxWidth
    Box(
        modifier =
        Modifier
            .clickable { onPartyClick(party) }
            .size(maxWidth)
            .padding(8.dp)
            .background(
                color = when (party.cover) {
                    0 -> TamadaTheme.colors.primaryPurple
                    1 -> TamadaTheme.colors.primaryBlue
                    2 -> TamadaTheme.colors.primaryPink
                    else -> TamadaTheme.colors.primaryGreen
                },
                shape = TamadaTheme.shapes.mediumSmall,
            )
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = party.name,
            color = TamadaTheme.colors.textWhite,
            style = TamadaTheme.typography.head,
        )
    }
    if (party.isNew) {
        Text(
            modifier =
            Modifier
                .wrapContentSize()
                .background(
                    TamadaTheme.colors.textHeader,
                    TamadaTheme.shapes.mediumSmall,
                )
                .padding(8.dp),
            text = stringResource(id = R.string.home_main_screen_party_tag),
            color = TamadaTheme.colors.textWhite,
            style = TamadaTheme.typography.body,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TamadaTheme {
        Party(
            party = Party(1L, "B-day", 0, true, false),
            onPartyClick = {},
        )
    }
}
