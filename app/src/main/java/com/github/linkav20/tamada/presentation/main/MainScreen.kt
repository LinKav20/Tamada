package com.github.linkav20.tamada.presentation.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.github.linkav20.core.domain.entity.ExpensesBottomNavigationItem
import com.github.linkav20.core.domain.entity.GuestsBottomNavigationItem
import com.github.linkav20.core.domain.entity.InfoBottomNavigationItem
import com.github.linkav20.core.domain.entity.ListsBottomNavigationItem
import com.github.linkav20.core.error.ErrorManager
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.navigation.bottomnavigation.BottomBar
import com.github.linkav20.core.notification.SnackbarManager
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.home.navigation.HomeGraphDestination
import com.github.linkav20.tamada.navigation.AppNavHost
import com.github.linkav20.tamada.notification.SnackbarHost
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.messaging.messaging

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    errorManager: ErrorManager,
    errorMapper: ErrorMapper,
    snackbarManager: SnackbarManager,
    onLoadingStateChanged: (Boolean) -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    Firebase.messaging.subscribeToTopic("test_Artem")
        .addOnCompleteListener { task ->
            var msg = "Subscribed"
            if (!task.isSuccessful) {
                msg = "Subscribe failed"
            }
            Log.d("MY_", msg)
        }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding(),
        backgroundColor = getBackgroundColor(scheme = ColorScheme.MAIN),
        bottomBar = {
            BottomBar(
                navController = navController,
                bottomNavigationEntries = listOf(
                    InfoBottomNavigationItem,
                    GuestsBottomNavigationItem,
                    ListsBottomNavigationItem,
                    ExpensesBottomNavigationItem,
                ),
                isFirstTabLoad = viewModel::isFirstTabLoad
            )
        },
    ) { paddings ->
        Box(modifier = Modifier.padding(paddings)) {
            AppNavHost(
                navController = navController,
                startDestination = HomeGraphDestination.route(),
                errorMapper = errorMapper
            )
        }
    }
    SnackbarHost(
        snackbarManager = snackbarManager,
        errorManager = errorManager,
        mainViewModel = viewModel,
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
    )
    LaunchedEffect(state.loading) {
        onLoadingStateChanged(state.loading)
    }
}
