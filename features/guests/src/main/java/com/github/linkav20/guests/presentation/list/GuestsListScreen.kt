package com.github.linkav20.guests.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.core.utils.copyToClipboard
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaGradientDisclaimer
import com.github.linkav20.coreui.ui.TamadaTextWithBackground
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getSecondaryColor
import com.github.linkav20.coreui.utils.getUserAvatar
import com.github.linkav20.coreui.R as CoreUi
import com.github.linkav20.guests.R
import com.github.linkav20.guests.domain.model.User

@Composable
fun GuestsListScreen(
    viewModel: GuestsListViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

    Content(
        loading = state.loading,
        link = state.link,
        infoShownCount = state.infoShownCount,
        isUsersEmpty = state.isUsersEmpty,
        canUserEdit = state.isUserEdit,
        isEditable = state.isEditable,
        managers = state.managers(),
        guestsExactlyCome = state.guestsExactlyCome(),
        guestsProbablyCome = state.guestsProbablyCome(),
        onAddToAdminClick = viewModel::onAddToAdminClick,
        onDeleteFromAdminClick = viewModel::onDeleteFromAdminClick,
        onEditClick = viewModel::onEditClick,
        onSaveClick = viewModel::onSaveClick,
        onDeleteClick = viewModel::onDeleteClick,
        onUpdateLink = viewModel::onUpdateLink,
        onBackClick = { navController.navigateUp() }
    )
}

@Composable
private fun Content(
    loading: Boolean,
    link: String?,
    infoShownCount: Int?,
    isUsersEmpty: Boolean,
    canUserEdit: Boolean,
    isEditable: Boolean,
    managers: List<User>,
    guestsProbablyCome: List<User>,
    guestsExactlyCome: List<User>,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onUpdateLink: () -> Unit,
    onDeleteFromAdminClick: (User) -> Unit,
    onAddToAdminClick: (User) -> Unit,
    onDeleteClick: (User) -> Unit,
    onBackClick: () -> Unit
) = Scaffold(
    backgroundColor = getBackgroundColor(scheme = ColorScheme.GUESTS),
    topBar = {
        TamadaTopBar(
            colorScheme = ColorScheme.GUESTS,
            title = stringResource(id = R.string.guests_list_title),
            onBackClick = onBackClick
        )
    }
) { paddings ->
    if (loading) {
        TamadaFullscreenLoader(scheme = ColorScheme.GUESTS)
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(paddings)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { InfoDisclaimer(infoShownCount = infoShownCount) }
            item {
                InviteGuestsCard(
                    link = link,
                    onUpdateLink = onUpdateLink
                )
            }
            item {
                TamadaCard(
                    colorScheme = ColorScheme.GUESTS
                ) {
                    UsersListN(
                        isUsersEmpty = isUsersEmpty,
                        canUserEdit = canUserEdit,
                        isEditable = isEditable,
                        managers = managers,
                        guestsProbablyCome = guestsProbablyCome,
                        guestsExactlyCome = guestsExactlyCome,
                        onEditClick = onEditClick,
                        onSaveClick = onSaveClick,
                        onDeleteFromAdminClick = onDeleteFromAdminClick,
                        onAddToAdminClick = onAddToAdminClick,
                        onDeleteClick = onDeleteClick
                    )
                }
            }
            item { 
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun UsersListN(
    isUsersEmpty: Boolean,
    canUserEdit: Boolean,
    isEditable: Boolean,
    managers: List<User>,
    guestsProbablyCome: List<User>,
    guestsExactlyCome: List<User>,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onDeleteFromAdminClick: (User) -> Unit,
    onAddToAdminClick: (User) -> Unit,
    onDeleteClick: (User) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEditable) {
                Text(
                    text = stringResource(id = R.string.guests_list_edit_users),
                    style = TamadaTheme.typography.head,
                    color = TamadaTheme.colors.textHeader,
                )
            } else {
                Text(
                    text = if (canUserEdit) {
                        stringResource(id = R.string.guests_list_admin_title)
                    } else {
                        stringResource(id = R.string.guests_list_admin_title_editable)
                    },
                    style = TamadaTheme.typography.head,
                    color = TamadaTheme.colors.textHeader,
                )
                if (!isUsersEmpty && canUserEdit) {
                    TamadaButton(
                        iconPainter = painterResource(id = CoreUi.drawable.edit_icon),
                        colorScheme = ColorScheme.GUESTS,
                        type = ButtonType.SECONDARY,
                        onClick = onEditClick
                    )
                }
            }
        }
        if (isEditable) {
            Text(
                text = stringResource(R.string.guests_list_admin_title),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textHeader,
            )
        }
        if (managers.isEmpty()) {
            Text(
                text = stringResource(id = R.string.guests_list_admin_subtitle),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain,
            )
        } else {
            managers.forEach {
                UserItem(
                    isEditable = isEditable,
                    user = it,
                    onDeleteClick = onDeleteClick,
                    onStarClick = onDeleteFromAdminClick,
                )
            }
        }
        if (!isEditable) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (isEditable) {
            Text(
                text = stringResource(id = R.string.guests_list_guests_title_guests),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textHeader,
            )
        } else {
            Text(
                text = if (guestsExactlyCome.isEmpty() && guestsProbablyCome.isEmpty()) {
                    stringResource(id = R.string.guests_list_guests_title)
                } else {
                    stringResource(id = R.string.guests_list_guests_title_guests)
                },
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader,
            )
        }
        if (guestsExactlyCome.isNotEmpty() || guestsProbablyCome.isNotEmpty()) {
            if (!isEditable) {
                Text(
                    text = stringResource(id = R.string.guests_list_guests_exactly_come),
                    style = TamadaTheme.typography.caption,
                    color = TamadaTheme.colors.textHeader,
                )
            }
            guestsExactlyCome.forEach {
                UserItem(
                    isEditable = isEditable,
                    user = it,
                    onDeleteClick = onDeleteClick,
                    onStarClick = onAddToAdminClick,
                )
            }
            if (!isEditable) {
                Text(
                    text = stringResource(id = R.string.guests_list_guests_probably_come),
                    style = TamadaTheme.typography.caption,
                    color = TamadaTheme.colors.textHeader,
                )
            }
            guestsProbablyCome.forEach {
                UserItem(
                    isEditable = isEditable,
                    user = it,
                    onDeleteClick = onDeleteClick,
                    onStarClick = onAddToAdminClick,
                )
            }
        } else {
            Text(
                text = stringResource(id = R.string.guests_list_guests_subtitle),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain,
            )
        }
        if (!isEditable) {
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (isEditable) {
            TamadaButton(
                title = stringResource(id = R.string.guests_list_save),
                colorScheme = ColorScheme.GUESTS,
                onClick = onSaveClick
            )
        }
    }
}

@Composable
private fun UsersList(
    isUsersEmpty: Boolean,
    canUserEdit: Boolean,
    isEditable: Boolean,
    managers: List<User>,
    guestsProbablyCome: List<User>,
    guestsExactlyCome: List<User>,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onDeleteFromAdminClick: (User) -> Unit,
    onAddToAdminClick: (User) -> Unit,
    onDeleteClick: (User) -> Unit,
) = LazyColumn(
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEditable) {
                Text(
                    text = stringResource(id = R.string.guests_list_edit_users),
                    style = TamadaTheme.typography.head,
                    color = TamadaTheme.colors.textHeader,
                )
            } else {
                Text(
                    text = if (canUserEdit) {
                        stringResource(id = R.string.guests_list_admin_title)
                    } else {
                        stringResource(id = R.string.guests_list_admin_title_editable)
                    },
                    style = TamadaTheme.typography.head,
                    color = TamadaTheme.colors.textHeader,
                )
                if (!isUsersEmpty && canUserEdit) {
                    TamadaButton(
                        iconPainter = painterResource(id = CoreUi.drawable.edit_icon),
                        colorScheme = ColorScheme.GUESTS,
                        type = ButtonType.SECONDARY,
                        onClick = onEditClick
                    )
                }
            }
        }
    }
    if (isEditable) {
        item {
            Text(
                text = stringResource(R.string.guests_list_admin_title),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textHeader,
            )
        }
    }
    if (managers.isEmpty()) {
        item {
            Text(
                text = stringResource(id = R.string.guests_list_admin_subtitle),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain,
            )
        }
    } else {
        items(managers) {
            UserItem(
                isEditable = isEditable,
                user = it,
                onDeleteClick = onDeleteClick,
                onStarClick = onDeleteFromAdminClick,
            )
        }
    }
    if (!isEditable) {
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
    if (isEditable) {
        item {
            Text(
                text = stringResource(id = R.string.guests_list_guests_title_guests),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textHeader,
            )
        }
    } else {
        item {
            Text(
                text = if (guestsExactlyCome.isEmpty() && guestsProbablyCome.isEmpty()) {
                    stringResource(id = R.string.guests_list_guests_title)
                } else {
                    stringResource(id = R.string.guests_list_guests_title_guests)
                },
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader,
            )
        }
    }
    if (guestsExactlyCome.isNotEmpty() || guestsProbablyCome.isNotEmpty()) {
        if (!isEditable) {
            item {
                Text(
                    text = stringResource(id = R.string.guests_list_guests_exactly_come),
                    style = TamadaTheme.typography.caption,
                    color = TamadaTheme.colors.textHeader,
                )
            }
        }
        items(guestsExactlyCome) {
            UserItem(
                isEditable = isEditable,
                user = it,
                onDeleteClick = onDeleteClick,
                onStarClick = onAddToAdminClick,
            )
        }
        if (!isEditable) {
            item {
                Text(
                    text = stringResource(id = R.string.guests_list_guests_probably_come),
                    style = TamadaTheme.typography.caption,
                    color = TamadaTheme.colors.textHeader,
                )
            }
        }
        items(guestsProbablyCome) {
            UserItem(
                isEditable = isEditable,
                user = it,
                onDeleteClick = onDeleteClick,
                onStarClick = onAddToAdminClick,
            )
        }
    } else {
        item {
            Text(
                text = stringResource(id = R.string.guests_list_guests_subtitle),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain,
            )
        }
    }
    if (!isEditable) {
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
    if (isEditable) {
        item {
            TamadaButton(
                title = stringResource(id = R.string.guests_list_save),
                colorScheme = ColorScheme.GUESTS,
                onClick = onSaveClick
            )
        }
    }
}

@Composable
private fun InviteGuestsCard(
    link: String?,
    onUpdateLink: () -> Unit,
) = TamadaCard(
    colorScheme = ColorScheme.GUESTS
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.guests_list_invite_title),
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.guests_list_invite_subtitle),
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TamadaButton(
                iconPainter = painterResource(id = CoreUi.drawable.recycle_icon),
                colorScheme = ColorScheme.GUESTS,
                type = ButtonType.SECONDARY,
                onClick = onUpdateLink
            )
            val scroll = rememberScrollState(0)
            TamadaTextWithBackground(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(scroll),
                text = link ?: "",
                colorScheme = ColorScheme.GUESTS
            )
            val context = LocalContext.current
            TamadaButton(
                colorScheme = ColorScheme.GUESTS,
                iconPainter = painterResource(id = CoreUi.drawable.copy_icon),
                onClick = { context.copyToClipboard(link ?: "") }
            )
        }
    }
}


@Composable
private fun UserItem(
    isEditable: Boolean,
    user: User,
    onStarClick: (User) -> Unit,
    onDeleteClick: (User) -> Unit,
) = Row(
    verticalAlignment = Alignment.CenterVertically
) {
    if (isEditable) {
        TamadaButton(
            colorScheme = ColorScheme.GUESTS,
            elevation = 20.dp,
            type = if (user.role == User.UserRole.MANAGER) ButtonType.PRIMARY else ButtonType.SECONDARY,
            iconPainter = painterResource(id = CoreUi.drawable.star_icon),
            onClick = { onStarClick(user) }
        )
    }
    TamadaCard(
        colorScheme = ColorScheme.GUESTS,
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = getUserAvatar(id = user.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = user.name,
                style = TamadaTheme.typography.head,
                color = getPrimaryColor(scheme = ColorScheme.GUESTS)
            )
        }
    }
    if (isEditable) {
        TamadaButton(
            colorScheme = ColorScheme.GUESTS,
            iconPainter = painterResource(id = CoreUi.drawable.delete_icon),
            backgroundColor = TamadaTheme.colors.textWhite,
            iconColor = TamadaTheme.colors.statusNegative,
            elevation = 20.dp,
            onClick = { onDeleteClick(user) }
        )
    }
}

@Composable
private fun InfoDisclaimer(
    infoShownCount: Int?
) {
    TamadaGradientDisclaimer(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = stringResource(id = R.string.guests_list_info_shown_disclaimer_first),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textWhite
            )
            Text(
                text = stringResource(
                    R.string.guests_list_info_shown_disclaimer_second,
                    infoShownCount ?: "-"
                ),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textWhite,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}
