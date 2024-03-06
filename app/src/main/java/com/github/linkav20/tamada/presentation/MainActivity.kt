package com.github.linkav20.tamada.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.linkav20.core.error.ErrorManager
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.notification.SnackbarManager
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.tamada.presentation.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var errorMapper: ErrorMapper

    @Inject
    lateinit var snackbarManager: SnackbarManager

    @Inject
    lateinit var errorManager: ErrorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var keepOnScreenCondition = true
        installSplashScreen().setKeepOnScreenCondition { keepOnScreenCondition }
        setContent {
            TamadaTheme {
                MainScreen(
                    viewModel = hiltViewModel(),
                    errorManager = errorManager,
                    errorMapper = errorMapper,
                    snackbarManager = snackbarManager,
                    onLoadingStateChanged = { keepOnScreenCondition = it },
                )
            }
        }
    }
}
