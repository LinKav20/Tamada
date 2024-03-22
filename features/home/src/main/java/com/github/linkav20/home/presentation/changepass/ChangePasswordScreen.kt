package com.github.linkav20.home.presentation.changepass

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaTextFiled
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.home.R
import com.github.linkav20.coreui.R as CoreR

@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value
    if (state.loading) {
        TamadaFullscreenLoader(scheme = ColorScheme.LISTS)
    } else {
        Content(
            state = state,
            onChangePasswordClick = viewModel::onPasswordUpdateClick,
            onCurrentPasswordChange = viewModel::onPasswordChange,
            onNewPasswordChange = viewModel::onNewPasswordChange,
            onRepeatedPasswordChange = viewModel::onRepeatedNewPasswordChange,
            onBackClick = { navController.navigateUp() },
            onShowPasswordClick = viewModel::onPasswordShowChange,
            onShowNewPasswordClick = viewModel::onNewPasswordShowChange,
            onShowRepeatedPasswordClick = viewModel::onRepeatedNewPasswordShowChange,
        )
    }

    LaunchedEffect(state.action) {
        if (state.action == ChangePasswordState.Action.BACK) {
            navController.navigateUp()
        }
    }
}

@Composable
private fun Content(
    state: ChangePasswordState,
    onCurrentPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onRepeatedPasswordChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onShowPasswordClick: () -> Unit,
    onShowNewPasswordClick: () -> Unit,
    onShowRepeatedPasswordClick: () -> Unit
) = Scaffold(
    backgroundColor = TamadaTheme.colors.backgroundPrimary,
    topBar = {
        TamadaTopBar(
            title = stringResource(id = R.string.change_password_title),
            onBackClick = onBackClick,
            transparent = true
        )
    }
) { paddingValues ->
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        PasswordTextFieldWithTitle(
            title = stringResource(id = R.string.change_password_current_title),
            value = state.currentPassword,
            showPassword = state.showCurrentPassword,
            hint = stringResource(id = R.string.change_password_current_hint),
            onShowPasswordClick = onShowPasswordClick,
            onPasswordChanged = onCurrentPasswordChange
        )
        PasswordTextFieldWithTitle(
            title = stringResource(id = R.string.change_password_new_title),
            value = state.newPassword,
            showPassword = state.showNewPassword,
            hint = stringResource(id = R.string.change_password_new_hint),
            onShowPasswordClick = onShowNewPasswordClick,
            onPasswordChanged = onNewPasswordChange
        )
        PasswordTextFieldWithTitle(
            title = stringResource(id = R.string.change_password_repeated_title),
            value = state.repeatedPassword,
            showPassword = state.showRepeatedPassword,
            hint = stringResource(id = R.string.change_password_new_hint),
            onShowPasswordClick = onShowRepeatedPasswordClick,
            onPasswordChanged = onRepeatedPasswordChange
        )
        TamadaButton(
            title = stringResource(id = R.string.change_password_change_button),
            onClick = onChangePasswordClick,
            colorScheme = ColorScheme.LISTS
        )
        Spacer(modifier = Modifier.weight(3f))
    }
}

@Composable
private fun PasswordTextFieldWithTitle(
    title: String,
    value: String,
    showPassword: Boolean,
    hint: String,
    onShowPasswordClick: () -> Unit,
    onPasswordChanged: (String) -> Unit
) = Column {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        style = TamadaTheme.typography.body,
        color = TamadaTheme.colors.textMain
    )
    Spacer(modifier = Modifier.height(8.dp))
    TamadaTextFiled(
        value = value,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = hint,
        onValueChange = onPasswordChanged,
        maxLines = 1,
        colorScheme = ColorScheme.LISTS,
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onShowPasswordClick() },
                painter = if (showPassword) {
                    painterResource(id = CoreR.drawable.outline_remove_red_eye_24)
                } else {
                    painterResource(id = CoreR.drawable.eyebrow)
                },
                contentDescription = null
            )
        }
    )
}

@Composable
@Preview
private fun Preview() {
    TamadaTheme {
        Content(
            state = ChangePasswordState(),
            onBackClick = {},
            onRepeatedPasswordChange = {},
            onNewPasswordChange = {},
            onCurrentPasswordChange = {},
            onChangePasswordClick = {},
            onShowNewPasswordClick = {},
            onShowPasswordClick = {},
            onShowRepeatedPasswordClick = {}
        )
    }
}