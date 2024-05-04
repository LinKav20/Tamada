package com.github.linkav20.tamada.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.error.ErrorManager
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.core.notification.SnackbarManager
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.tamada.R
import com.github.linkav20.tamada.presentation.invitation.InvitationActivity
import com.github.linkav20.tamada.presentation.invitation.PARTY_ID
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

    @Inject
    lateinit var reactUseCase: ReactUseCase

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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent) {
        try {
            val id = intent.data.toString().split('/').last()
            val idToInt = id.toInt()
            startActivity(Intent(this, InvitationActivity::class.java).apply {
                putExtra(PARTY_ID, idToInt)
            })
        } catch (e: Exception) {
            reactUseCase.invoke(
                title = applicationContext.getString(R.string.error_parsing_deeplink_title),
                style = ReactionStyle.WARNING
            )
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
