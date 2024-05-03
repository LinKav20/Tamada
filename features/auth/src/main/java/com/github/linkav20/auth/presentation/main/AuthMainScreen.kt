package com.github.linkav20.auth.presentation.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.auth.R
import com.github.linkav20.auth.navigation.LoginDestination
import com.github.linkav20.auth.navigation.SignUpDestination
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.R as CoreR

@Composable
fun AuthMainScreen(
    viewModel: AuthMainScreenViewModel,
    navController: NavController,
) {
    val state = viewModel.state.collectAsState().value

    if (state.loading) {
        TamadaFullscreenLoader()
    } else {
        Content(
            onSignUpClick = {
                navController.navigate(SignUpDestination.route())
            },
            onLoginClick = {
                navController.navigate(LoginDestination.route())
            },
        )
    }
    val activity = (LocalContext.current as? Activity)
    BackHandler {
        activity?.finish()
    }
}

@Composable
private fun Content(
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = CoreR.drawable.logo),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(44.dp))
        Text(
            text = stringResource(id = R.string.auth_main_screen_hello_message),
            style = TamadaTheme.typography.body,
            color = TamadaTheme.colors.textHeader,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.auth_main_screen_about_us_message),
            style = TamadaTheme.typography.body,
            color = TamadaTheme.colors.textMain,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(2f))
        TamadaButton(
            onClick = onLoginClick,
            title = stringResource(R.string.auth_main_screen_login_title),
            colorScheme = ColorScheme.LISTS,
        )
        Spacer(modifier = Modifier.height(8.dp))
        TamadaButton(
            type = ButtonType.OUTLINE,
            onClick = onSignUpClick,
            title = stringResource(R.string.auth_main_screen_signup_title),
            colorScheme = ColorScheme.LISTS,
        )
        Spacer(modifier = Modifier.weight(0.5f))
    }
}
