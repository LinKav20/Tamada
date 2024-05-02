package com.github.linkav20.tamada.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.linkav20.core.error.ErrorManager
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.notification.SnackbarManager
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.tamada.presentation.main.MainScreen
import com.squareup.leakcanary.core.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
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
        initLog()
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

    private fun initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(Timber.DebugTree())
        }
    }
}
