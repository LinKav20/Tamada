package com.github.linkav20.home.presentation.lk

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.auth.navigation.AuthGraphDestination
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.utils.OnLifecycleStart
import com.github.linkav20.core.utils.copyToClipboard
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.DebounceIconButton
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaDialog
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaTextFiled
import com.github.linkav20.coreui.ui.TamadaTextWithBackground
import com.github.linkav20.home.R
import com.github.linkav20.coreui.R as CoreR
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getUserAvatar
import com.github.linkav20.home.navigation.ChangeAvatarDestination
import com.github.linkav20.home.navigation.ChangePasswordDestination


@Composable
fun LkScreen(
    viewModel: LkViewModel,
    navController: NavController,
    errorMapper: ErrorMapper
) {
    val state = viewModel.state.collectAsState().value

    if (state.showDialog) {
        TamadaDialog(
            title = stringResource(id = R.string.lk_delete_account_dialog_title),
            body = {
                Column {
                    Text(
                        text = stringResource(id = R.string.lk_delete_account_dialog_password),
                        style = TamadaTheme.typography.caption,
                        color = TamadaTheme.colors.textMain
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TamadaTextFiled(
                        value = state.password,
                        visualTransformation = if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        placeholder = stringResource(id = R.string.lk_delete_account_dialog_password_hint),
                        onValueChange = viewModel::onPasswordChanged,
                        maxLines = 1,
                        colorScheme = ColorScheme.LISTS,
                        trailingIcon = {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { viewModel.onShowPasswordClick() },
                                painter = if (state.showPassword) {
                                    painterResource(id = CoreR.drawable.outline_remove_red_eye_24)
                                } else {
                                    painterResource(id = CoreR.drawable.eyebrow)
                                },
                                contentDescription = null,
                                tint = getPrimaryColor(scheme = ColorScheme.LISTS)
                            )
                        }
                    )
                }
            },
            onClose = viewModel::onCloseDialog,
            colorScheme = ColorScheme.LISTS,
            onConfirm = viewModel::onDeleteAccount
        )
    }

    Content(
        state = state,
        onBackClick = { navController.navigateUp() },
        onDeleteAccountClick = viewModel::onShowDialog,
        onChangePasswordClick = { navController.navigate(ChangePasswordDestination.route()) },
        onEditInfoClick = viewModel::onEditInfoClick,
        onSaveInfoClick = viewModel::onSaveInfoClick,
        onEditWalletClick = viewModel::onEditWalletClick,
        onSaveWalletClick = viewModel::onSaveWalletClick,
        onChangeAvatarClick = {
            val id = state.user?.avatar ?: return@Content
            navController.navigate(ChangeAvatarDestination.createRoute(id))
        },
        onLoginChanged = viewModel::onLoginChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onCardNumberChanged = viewModel::onCardNumberChanged,
        onPhoneNumberChanged = viewModel::onCardPhoneNumberChanged,
        onBankChanged = viewModel::onCardBankChanged,
        onCardOwnerChanged = viewModel::onCardOwnerChanged,
        onLogoutClick = viewModel::onLogoutClick,
        onError = { throwable ->
            errorMapper.OnError(
                throwable,
                viewModel::onRetry,
                ColorScheme.MAIN
            )
        }
    )

    OnLifecycleStart {
        viewModel.onStart()
    }

    LaunchedEffect(state.action) {
        if (state.action == LkState.Action.AUTH) {
            navController.navigateUp()
            navController.navigate(AuthGraphDestination.route())
        }
    }
}

@Composable
private fun Content(
    state: LkState,
    onBackClick: () -> Unit,
    onDeleteAccountClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onEditInfoClick: () -> Unit,
    onSaveInfoClick: () -> Unit,
    onEditWalletClick: () -> Unit,
    onSaveWalletClick: () -> Unit,
    onChangeAvatarClick: () -> Unit,
    onLoginChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onCardNumberChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onBankChanged: (String) -> Unit,
    onCardOwnerChanged: (String) -> Unit,
    onLogoutClick: () -> Unit,
    onError: @Composable (Throwable) -> Unit
) {
    Scaffold(
        backgroundColor = TamadaTheme.colors.backgroundPrimary,
        topBar = {
            TamadaTopBar(
                title = stringResource(id = R.string.lk_screen_title),
                onBackClick = onBackClick,
                actions = {
                    if (state.user != null) {
                        DebounceIconButton(onLogoutClick) {
                            Icon(
                                painterResource(CoreR.drawable.baseline_logout_24),
                                contentDescription = null,
                                tint = TamadaTheme.colors.textMain,
                            )
                        }
                    }
                },
                transparent = true
            )
        },
        bottomBar = {
            if (state.user != null) {
                Column(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                ) {
                    TamadaCard(colorScheme = ColorScheme.LISTS) {
                        TamadaButton(
                            onClick = onChangePasswordClick,
                            title = stringResource(id = R.string.lk_change_password_button),
                            type = ButtonType.OUTLINE,
                            colorScheme = ColorScheme.LISTS
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TamadaCard {
                        TamadaButton(
                            onClick = onDeleteAccountClick,
                            title = stringResource(id = R.string.lk_delete_account_button),
                            type = ButtonType.ERROR
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        if (state.loading) {
            TamadaFullscreenLoader(scheme = ColorScheme.LISTS)
        } else if (state.user != null) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(24.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(
                        painter = getUserAvatar(state.user.avatar),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 20.dp)
                            .size(64.dp)
                            .clip(CircleShape),
                    )
                    TamadaButton(
                        elevation = 50.dp,
                        iconPainter = painterResource(id = CoreR.drawable.edit_icon),
                        type = ButtonType.SECONDARY,
                        backgroundColor = TamadaTheme.colors.backgroundPrimary,
                        onClick = onChangeAvatarClick,
                        colorScheme = ColorScheme.LISTS
                    )
                }
                if (state.isInfoEdit) {
                    EditableInfoComponent(
                        login = state.user.login,
                        email = state.user.email,
                        onEmailChange = onEmailChanged,
                        onLoginChange = onLoginChanged,
                        onSaveClick = onSaveInfoClick
                    )
                } else {
                    InfoComponent(
                        login = state.user.login,
                        email = state.user.email,
                        onEditClick = onEditInfoClick
                    )
                }
                if (state.isWalletEdit) {
                    EditableWalletComponent(
                        cartNumber = state.user.wallet?.cardNumber,
                        phoneNumber = state.user.wallet?.cardPhoneNumber,
                        cardOwner = state.user.wallet?.cardOwner,
                        bank = state.user.wallet?.cardBank,
                        onCardNumberChanged = onCardNumberChanged,
                        onBankChanged = onBankChanged,
                        onSaveDataClick = onSaveWalletClick,
                        onPhoneNumberChanged = onPhoneNumberChanged,
                        onCardOwnerChanged = onCardOwnerChanged
                    )
                } else {
                    WalletComponent(
                        cartNumber = state.user.wallet?.cardNumber,
                        phoneNumber = state.user.wallet?.cardPhoneNumber,
                        owner = state.user.wallet?.cardOwner,
                        bank = state.user.wallet?.cardBank,
                        onEditClick = onEditWalletClick
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        } else if (state.exception != null) {
            onError(state.exception)
        }
    }
}

@Composable
private fun EditableInfoComponent(
    login: String,
    email: String,
    onLoginChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSaveClick: () -> Unit
) = TamadaCard(colorScheme = ColorScheme.LISTS) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TamadaTextFiled(
            placeholder = stringResource(id = R.string.lk_login_placeholder),
            onValueChange = onLoginChange,
            value = login,
            colorScheme = ColorScheme.LISTS
        )
        TamadaTextFiled(
            placeholder = stringResource(id = R.string.lk_email_placeholder),
            onValueChange = onEmailChange,
            value = email,
            colorScheme = ColorScheme.LISTS
        )
        TamadaButton(
            title = stringResource(id = R.string.lk_info_save),
            onClick = onSaveClick,
            colorScheme = ColorScheme.LISTS
        )
    }
}

@Composable
private fun InfoComponent(
    login: String,
    email: String,
    onEditClick: () -> Unit
) = TamadaCard(colorScheme = ColorScheme.LISTS) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Item(
                title = stringResource(id = R.string.lk_login_title),
                subtitle = login
            )
            Item(
                title = stringResource(id = R.string.lk_email_title),
                subtitle = email
            )
        }
        TamadaButton(
            iconPainter = painterResource(id = CoreR.drawable.edit_icon),
            type = ButtonType.SECONDARY,
            onClick = onEditClick,
            colorScheme = ColorScheme.LISTS
        )
    }
}

@Composable
private fun WalletComponent(
    cartNumber: String?,
    phoneNumber: String?,
    owner: String?,
    bank: String?,
    onEditClick: () -> Unit
) = TamadaCard(colorScheme = ColorScheme.LISTS) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.lk_wallet_title),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader,
            )
            Row {
                if (cartNumber.isNullOrEmpty() || phoneNumber.isNullOrEmpty()) {
                    TamadaTextWithBackground(
                        text = stringResource(id = R.string.lk_wallet_need_to_fill_title),
                        backgroundColor = TamadaTheme.colors.statusWarning.copy(alpha = 0.7f),
                        textColor = TamadaTheme.colors.textHint
                    )
                }
                TamadaButton(
                    iconPainter = painterResource(id = CoreR.drawable.edit_icon),
                    type = ButtonType.SECONDARY,
                    onClick = onEditClick,
                    colorScheme = ColorScheme.LISTS
                )
            }
        }
        Text(
            text = stringResource(id = R.string.lk_wallet_subtitle),
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain
        )
        if (!cartNumber.isNullOrEmpty() && !phoneNumber.isNullOrEmpty()) {
            val context = LocalContext.current
            Row(
                modifier = Modifier.height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val scroll = rememberScrollState(0)
                TamadaTextWithBackground(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(scroll),
                    text = cartNumber,
                    colorScheme = ColorScheme.LISTS
                )
                TamadaButton(
                    iconPainter = painterResource(id = CoreR.drawable.copy_icon),
                    onClick = { context.copyToClipboard(cartNumber) },
                    colorScheme = ColorScheme.LISTS
                )
            }
            Row(
                modifier = Modifier.height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val scroll = rememberScrollState(0)
                TamadaTextWithBackground(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(scroll),
                    text = phoneNumber,
                    colorScheme = ColorScheme.LISTS
                )
                TamadaButton(
                    iconPainter = painterResource(id = CoreR.drawable.copy_icon),
                    onClick = { context.copyToClipboard(phoneNumber) },
                    colorScheme = ColorScheme.LISTS
                )
            }
            if (bank != null && owner != null) BankInformation(bank = bank, owner = owner)
        } else {
            Text(
                text = stringResource(id = R.string.lk_wallet_empty),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditableWalletComponent(
    cartNumber: String?,
    phoneNumber: String?,
    cardOwner: String?,
    bank: String?,
    onCardNumberChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onBankChanged: (String) -> Unit,
    onCardOwnerChanged: (String) -> Unit,
    onSaveDataClick: () -> Unit
) = TamadaCard(colorScheme = ColorScheme.LISTS) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Text(
            text = stringResource(id = R.string.lk_wallet_title),
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader,
        )
        Text(
            text = stringResource(id = R.string.lk_wallet_subtitle),
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain
        )
        TamadaTextFiled(
            placeholder = stringResource(id = R.string.lk_wallet_card_number_placeholder),
            onValueChange = onCardNumberChanged,
            value = cartNumber ?: "",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            colorScheme = ColorScheme.LISTS
        )
        TamadaTextFiled(
            placeholder = stringResource(id = R.string.lk_wallet_phone_number_placeholder),
            onValueChange = onPhoneNumberChanged,
            value = phoneNumber ?: "",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            colorScheme = ColorScheme.LISTS
        )
        TamadaTextFiled(
            placeholder = stringResource(id = R.string.lk_wallet_card_owner_placeholder),
            onValueChange = onCardOwnerChanged,
            value = cardOwner ?: "",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            colorScheme = ColorScheme.LISTS
        )
        TamadaTextFiled(
            placeholder = stringResource(id = R.string.lk_wallet_bank_placeholder),
            onValueChange = onBankChanged,
            value = bank ?: "",
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            colorScheme = ColorScheme.LISTS
        )
        TamadaButton(
            title = stringResource(id = R.string.lk_wallet_save),
            onClick = onSaveDataClick,
            colorScheme = ColorScheme.LISTS
        )
    }
}

@Composable
fun BankInformation(
    bank: String,
    owner: String
) = Column {
    Text(
        text = stringResource(id = R.string.lk_wallet_bank, bank),
        style = TamadaTheme.typography.caption,
        color = TamadaTheme.colors.textMain
    )
    Text(
        text = stringResource(id = R.string.lk_wallet_card_owner, owner),
        style = TamadaTheme.typography.caption,
        color = TamadaTheme.colors.textMain
    )
}

@Composable
private fun Item(
    title: String,
    subtitle: String
) = Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        color = TamadaTheme.colors.textMain,
        style = TamadaTheme.typography.body
    )
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = subtitle,
        color = getPrimaryColor(scheme = ColorScheme.LISTS),
        style = TamadaTheme.typography.head
    )
}
