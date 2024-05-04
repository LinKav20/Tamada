package com.github.linkav20.invitation.presentation

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.linkav20.core.domain.entity.DomainException
import com.github.linkav20.core.utils.DateTimeUtils
import com.github.linkav20.core.utils.OnLifecycleStart
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.invitation.R
import com.github.linkav20.invitation.domain.model.Party

@Composable
fun InvitationScreen(
    id: Int,
    viewModel: InvitationViewModel,
) {
    val activity = (LocalContext.current as? Activity)
    val state = viewModel.state.collectAsState().value

    Content(
        state = state,
        onBackClick = { activity?.finish() },
        addUserToParty = viewModel::addUserToParty
    )

    OnLifecycleStart {
        viewModel.loadData(id)
    }

    LaunchedEffect(state.action) {
        if (state.action == InvitationState.Action.BACK) {
            activity?.finish()
        }
    }
}

@Composable
private fun Content(
    state: InvitationState,
    onBackClick: () -> Unit,
    addUserToParty: () -> Unit
) = Scaffold(
    modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .imePadding(),
    backgroundColor = TamadaTheme.colors.backgroundPrimary,
    topBar = {
        TamadaTopBar(
            transparent = true,
            title = stringResource(id = R.string.invitation_screen_title),
            onBackClick = onBackClick
        )
    },
    bottomBar = {
        when {
            state.exception != null -> BottomBar(
                stringResource(id = R.string.invitation_screen_unauth_error_button),
                onClick = onBackClick
            )

            state.loading -> {}
            state.party == null -> BottomBar(
                stringResource(id = R.string.invitation_screen_unauth_error_button),
                onClick = onBackClick
            )

            else -> BottomBar(
                stringResource(id = R.string.invitation_screen_add_button),
                onClick = addUserToParty
            )
        }
    }
) { paddingValues ->
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        when {
            state.exception != null -> ExceptionScreen(state.exception)
            state.loading -> TamadaFullscreenLoader()
            state.party == null -> NoParty()
            else -> Invitation(party = state.party)
        }
    }
}

@Composable
private fun Invitation(party: Party) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(40.dp),
    verticalArrangement = Arrangement.Center
) {
    Box(
        modifier = Modifier
            .size(300.dp)
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
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = party.name,
                color = TamadaTheme.colors.textWhite,
                style = TamadaTheme.typography.head,
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (party.address != null) {
                Text(
                    text = stringResource(R.string.invitation_screen_where, party.address),
                    color = TamadaTheme.colors.textWhite,
                    style = TamadaTheme.typography.body,
                )
            }
            if (party.startTime != null) {
                Text(
                    text = stringResource(
                        R.string.invitation_screen_when,
                        DateTimeUtils.toUiString(party.startTime)
                    ),
                    color = TamadaTheme.colors.textWhite,
                    style = TamadaTheme.typography.body,
                )
            }
        }
    }
}

@Composable
private fun ExceptionScreen(e: Exception) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(40.dp),
    verticalArrangement = Arrangement.Center
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = if (e is DomainException.Unauthorized) {
            stringResource(id = R.string.invitation_screen_unauth_error_title)
        } else {
            stringResource(id = R.string.invitation_screen_error_title)
        },
        color = TamadaTheme.colors.textHeader,
        style = TamadaTheme.typography.head,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        textAlign = TextAlign.Center,
        text = if (e is DomainException.Unauthorized) {
            stringResource(id = R.string.invitation_screen_unauth_error_subtitle)
        } else {
            stringResource(id = R.string.invitation_screen_error_subtitle)
        },
        color = TamadaTheme.colors.textHeader,
        style = TamadaTheme.typography.body
    )
}

@Composable
private fun NoParty() = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(40.dp),
    verticalArrangement = Arrangement.Center
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(id = R.string.invitation_screen_party_not_found),
        color = TamadaTheme.colors.textHeader,
        style = TamadaTheme.typography.head,
    )
}

@Composable
private fun BottomBar(
    title: String,
    onClick: () -> Unit
) = TamadaCard(
    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
    colorScheme = ColorScheme.LISTS
) {
    TamadaButton(
        title = title,
        colorScheme = ColorScheme.LISTS,
        type = ButtonType.OUTLINE,
        onClick = onClick
    )
}
