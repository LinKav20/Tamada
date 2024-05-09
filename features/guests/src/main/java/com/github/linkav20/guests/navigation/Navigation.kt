package com.github.linkav20.guests.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.navigation.common.GuestsTabDestination
import com.github.linkav20.core.navigation.composableRoute
import com.github.linkav20.guests.presentation.list.GuestsListScreen

fun NavGraphBuilder.guestsTabGraph(navController: NavController, errorMapper: ErrorMapper) {
    navigation(
        route = GuestsTabDestination.route(),
        startDestination = GuestsListDestination.route(),
    ) {
        composableRoute(GuestsListDestination) {
            GuestsListScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                errorMapper = errorMapper
            )
        }
    }
}
