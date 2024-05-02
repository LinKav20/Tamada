package com.github.linkav20.auth.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.auth.R
import com.github.linkav20.auth.navigation.AuthGraphDestination
import com.github.linkav20.coreui.R as CoreR
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaTextFiled
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController,
) {
    val state = viewModel.state.collectAsState().value

    Content(
        loading = state.loading,
        login = state.login,
        password = state.password,
        showPassword = state.isShowPassword,
        onLoginChanged = viewModel::onLoginUpdate,
        onLoginClick = viewModel::onLoginClick,
        onPasswordChanged = viewModel::onPasswordUpdate,
        onShowPasswordClick = viewModel::onShowPasswordClick
    )

    LaunchedEffect(state.action) {
        when (state.action) {
            LoginState.Action.MAIN -> {
                navController.popBackStack(
                    route = AuthGraphDestination.route(),
                    inclusive = true
                )
            }

            else -> {}
        }
        viewModel.nullifyAction()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Content(
    loading: Boolean,
    login: String,
    password: String,
    showPassword: Boolean,
    onLoginChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    onShowPasswordClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
            .imePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (loading) {
            TamadaFullscreenLoader(scheme = ColorScheme.LISTS)
        } else {
            val keyboardController = LocalSoftwareKeyboardController.current
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = CoreR.drawable.logo),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.login_screen_login_title),
                style = TamadaTheme.typography.body,
                color = TamadaTheme.colors.textMain
            )
            TamadaTextFiled(
                value = login,
                placeholder = stringResource(id = R.string.login_screen_login_hint),
                onValueChange = onLoginChanged,
                colorScheme = ColorScheme.LISTS,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.login_screen_password_title),
                style = TamadaTheme.typography.body,
                color = TamadaTheme.colors.textMain
            )
            TamadaTextFiled(
                value = password,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                placeholder = stringResource(id = R.string.login_screen_password_hint),
                onValueChange = onPasswordChanged,
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
                        tint = getPrimaryColor(scheme = ColorScheme.LISTS),
                        contentDescription = null
                    )
                },
                colorScheme = ColorScheme.LISTS,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
            )
            Spacer(modifier = Modifier.height(4.dp))
            TamadaButton(
                onClick = onLoginClick,
                title = stringResource(R.string.login_screen_login_button),
                colorScheme = ColorScheme.LISTS,
            )
            Spacer(modifier = Modifier.weight(2f))
        }
    }
}
