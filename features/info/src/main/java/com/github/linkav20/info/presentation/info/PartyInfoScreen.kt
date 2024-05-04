package com.github.linkav20.info.presentation.info

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.core.domain.entity.DomainException
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.utils.copyToClipboard
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaTextWithBackground
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.info.R
import com.github.linkav20.info.presentation.shared.EditableAddressComponent
import com.github.linkav20.info.presentation.shared.EditableImportantComponent
import com.github.linkav20.info.presentation.shared.EditableInfoComponent
import com.github.linkav20.info.presentation.shared.EditableThemeComponent
import java.time.OffsetDateTime
import com.github.linkav20.coreui.R as CoreR

@Composable
fun PartyInfoScreen(
    viewModel: PartyInfoViewModel,
    navController: NavController,
    errorMapper: ErrorMapper
) {
    val state = viewModel.state.collectAsState().value

    Content(
        state = state,
        onEditInfoClick = viewModel::onEditInfoClick,
        onSaveInfoClick = viewModel::onSaveInfoClick,
        onEditAddressClick = viewModel::onEditAddressClick,
        onSaveAddressClick = viewModel::onSaveAddressClick,
        onEditImportantClick = viewModel::onEditImportantClick,
        onSaveImportantClick = viewModel::onSaveImportantClick,
        onEditThemeClick = viewModel::onEditThemeClick,
        onSaveThemeClick = viewModel::onSaveThemeClick,
        onNameChanged = viewModel::onNameChanged,
        onStartDateChanged = viewModel::onStartDateChanged,
        onEndDateChanged = viewModel::onEndDateChanged,
        onAddressChanged = viewModel::onAddressChanged,
        onAddressAdditionalChanged = viewModel::onAddressAdditionalChanged,
        onImportantChanged = viewModel::onImportantChanged,
        onThemeChanged = viewModel::onThemeChanged,
        onMoodboardinkChanged = viewModel::onMoodboardinkChanged,
        onDressCodeChanged = viewModel::onDressCodeChanged,
        onBackClick = { navController.navigateUp() },
        onAddressLinkChanged = viewModel::onAddressLinkChanged,
        onError = { throwable ->
            errorMapper.OnError(
                throwable,
                viewModel::onRetry,
                ColorScheme.MAIN
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    state: PartyInfoState,
    onEditInfoClick: () -> Unit,
    onSaveInfoClick: () -> Unit,
    onEditAddressClick: () -> Unit,
    onSaveAddressClick: () -> Unit,
    onEditImportantClick: () -> Unit,
    onSaveImportantClick: () -> Unit,
    onEditThemeClick: () -> Unit,
    onSaveThemeClick: () -> Unit,
    onBackClick: () -> Unit,
    onNameChanged: (String) -> Unit,
    onStartDateChanged: (OffsetDateTime) -> Unit,
    onEndDateChanged: (OffsetDateTime) -> Unit,
    onAddressChanged: (String) -> Unit,
    onImportantChanged: (String) -> Unit,
    onAddressAdditionalChanged: (String) -> Unit,
    onThemeChanged: (String) -> Unit,
    onMoodboardinkChanged: (String) -> Unit,
    onDressCodeChanged: (String) -> Unit,
    onAddressLinkChanged: (String) -> Unit,
    onError: @Composable (Throwable) -> Unit,
) = Scaffold(
    modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding()
        .imePadding(),
    backgroundColor = getBackgroundColor(scheme = ColorScheme.MAIN),
    topBar = {
        TamadaTopBar(
            title = stringResource(id = R.string.info_party_title),
            onBackClick = onBackClick
        )
    },
) { paddings ->
    if (state.party == null) {
        state.error?.let { onError(it) }
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(paddings)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            item {
                if (state.isInfoSectionEditable) {
                    Box(modifier = Modifier.animateItemPlacement()) {
                        EditableInfoComponent(
                            name = state.party.name,
                            startTime = state.party.startTime,
                            endTime = state.party.endTime,
                            showButton = true,
                            onNameChanged = onNameChanged,
                            onStartDateChanged = onStartDateChanged,
                            onEndDateChanged = onEndDateChanged,
                            onSaveButtonClick = onSaveInfoClick
                        )
                    }
                } else {
                    Box(modifier = Modifier.animateItemPlacement()) {
                        InfoComponent(
                            title = state.party.name,
                            date = state.partyDate,
                            isManager = state.canEdit,
                            onClick = onEditInfoClick,
                        )
                    }
                }
            }
            item {
                if (state.isAddressSectionEditable) {
                    EditableAddressComponent(
                        address = state.party.address,
                        addressAdditional = state.party.addressAdditional,
                        addressLink = state.party.addressLink,
                        showButton = true,
                        onAddressChanged = onAddressChanged,
                        onAddressAdditionChanged = onAddressAdditionalChanged,
                        onAddressLinkChanged = onAddressLinkChanged,
                        onSaveButtonClick = onSaveAddressClick
                    )
                } else {
                    AddressComponent(
                        isManager = state.canEdit,
                        address = state.party.address,
                        addressLink = state.party.addressLink,
                        onEditAddressClick = onEditAddressClick
                    )
                }
            }
            item {
                if (state.isImportantSectionEditable) {
                    EditableImportantComponent(
                        important = state.party.important,
                        showButton = true,
                        onImportantChanged = onImportantChanged,
                        onSaveButtonClick = onSaveImportantClick
                    )
                } else {
                    ImportantComponent(
                        isManager = state.canEdit,
                        important = state.party.important,
                        onEditImportantClick = onEditImportantClick
                    )
                }
            }
            item {
                if (state.isThemeSectionEditable) {
                    EditableThemeComponent(
                        moodboadLink = state.party.moodboadLink,
                        theme = state.party.theme,
                        dressCode = state.party.dressCode,
                        showButton = true,
                        onSaveButtonClick = onSaveThemeClick,
                        onThemeChanged = onThemeChanged,
                        onDressCodeChanged = onDressCodeChanged,
                        onMoodboardinkChanged = onMoodboardinkChanged
                    )
                } else {
                    ThemeComponent(
                        isManager = state.canEdit,
                        theme = state.party.theme,
                        dressCode = state.party.dressCode,
                        moodboardLink = state.party.moodboadLink,
                        onEditThemeClick = onEditThemeClick
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
    if (state.loading) TamadaFullscreenLoader()
}

@Composable
private fun AddressComponent(
    address: String?,
    isManager: Boolean,
    addressLink: String?,
    onEditAddressClick: () -> Unit
) {
    TamadaCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.info_party_address_title),
                    style = TamadaTheme.typography.head,
                    color = TamadaTheme.colors.textHeader,
                )
                if (isManager) {
                    TamadaButton(
                        iconPainter = painterResource(id = CoreR.drawable.edit_icon),
                        type = ButtonType.SECONDARY,
                        onClick = onEditAddressClick,
                    )
                }
            }
            if (!address.isNullOrEmpty()) {
                Text(
                    text = address,
                    style = TamadaTheme.typography.caption,
                    color = TamadaTheme.colors.textHeader,
                )
            }
            if (!addressLink.isNullOrEmpty()) {
                LinkComponent(addressLink)
            }
        }
    }
}

@Composable
private fun ImportantComponent(
    isManager: Boolean,
    important: String?,
    onEditImportantClick: () -> Unit,
) {
    TamadaCard {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = R.string.info_party_important_info_title),
                    style = TamadaTheme.typography.head,
                    color = TamadaTheme.colors.textHeader,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (important.isNullOrEmpty()) {
                        stringResource(id = R.string.info_party_important_info_subtitle)
                    } else {
                        important
                    },
                    style = TamadaTheme.typography.caption,
                    color = if (important.isNullOrEmpty()) {
                        TamadaTheme.colors.textMain
                    } else {
                        TamadaTheme.colors.textHeader
                    },
                )
            }
            if (isManager) {
                TamadaButton(
                    iconPainter = painterResource(id = CoreR.drawable.edit_icon),
                    type = ButtonType.SECONDARY,
                    onClick = onEditImportantClick,
                )
            }
        }
    }
}

@Composable
private fun ThemeComponent(
    isManager: Boolean,
    theme: String?,
    dressCode: String?,
    moodboardLink: String?,
    onEditThemeClick: () -> Unit,
) {
    TamadaCard {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.info_party_theme_and_dresscode_title),
                        style = TamadaTheme.typography.head,
                        color = TamadaTheme.colors.textHeader,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (dressCode.isNullOrEmpty() && theme.isNullOrEmpty() && moodboardLink.isNullOrEmpty()) {
                        Text(
                            text = stringResource(id = R.string.info_party_theme_and_dresscode_subtitle),
                            style = TamadaTheme.typography.caption,
                            color = TamadaTheme.colors.textMain,
                        )
                    }
                }
                if (isManager) {
                    TamadaButton(
                        iconPainter = painterResource(id = CoreR.drawable.edit_icon),
                        type = ButtonType.SECONDARY,
                        onClick = onEditThemeClick,
                    )
                }
            }
            if (!theme.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.info_party_theme_title),
                    style = TamadaTheme.typography.body,
                    color = TamadaTheme.colors.textCaption,
                )
                Text(
                    text = theme,
                    style = TamadaTheme.typography.head,
                    color = TamadaTheme.colors.textHeader,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            if (!dressCode.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.info_party_dresscode_title),
                    style = TamadaTheme.typography.body,
                    color = TamadaTheme.colors.textCaption,
                )
                Text(
                    text = dressCode,
                    style = TamadaTheme.typography.head,
                    color = TamadaTheme.colors.textHeader,
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            if (!moodboardLink.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.info_party_moodboadlink_title),
                    style = TamadaTheme.typography.body,
                    color = TamadaTheme.colors.textCaption,
                )
                LinkComponent(moodboardLink)
            }
        }
    }
}

@Composable
private fun InfoComponent(
    title: String,
    date: String?,
    isManager: Boolean,
    onClick: () -> Unit,
) {
    TamadaCard {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = TamadaTheme.typography.head,
                    color = TamadaTheme.colors.textHeader,
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (date != null) {
                    Text(
                        text = date,
                        style = TamadaTheme.typography.body,
                        color = TamadaTheme.colors.textHeader,
                    )
                }
            }
            if (isManager) {
                TamadaButton(
                    iconPainter = painterResource(id = CoreR.drawable.edit_icon),
                    type = ButtonType.SECONDARY,
                    onClick = onClick,
                )
            }
        }
    }
}

@Composable
fun LinkComponent(link: String) = Row {
    val scroll = rememberScrollState(0)
    TamadaTextWithBackground(
        modifier = Modifier
            .weight(1f)
            .horizontalScroll(scroll),
        text = link
    )
    val context = LocalContext.current
    TamadaButton(
        iconPainter = painterResource(id = com.github.linkav20.coreui.R.drawable.copy_icon),
        onClick = { context.copyToClipboard(link) }
    )
}
