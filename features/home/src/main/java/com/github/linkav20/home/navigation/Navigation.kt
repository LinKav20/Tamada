package com.github.linkav20.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.navigation.composableRoute
import com.github.linkav20.home.presentation.lk.LkScreen
import com.github.linkav20.home.presentation.main.HomeMainScreen
import com.github.linkav20.home.presentation.party.PartyScreen

fun NavGraphBuilder.homeGraph(navController: NavController, errorMapper: ErrorMapper) {
    navigation(
        startDestination = HomeDestination.route(),
        route = HomeGraphDestination.route(),
    ) {
        composableRoute(HomeDestination) {
            HomeMainScreen(
                viewModel = hiltViewModel(),
                navController = navController,
            )
        }
        composableRoute(PartyScreenDestination) {
            PartyScreen(
                navController = navController,
            )
        }
        composableRoute(LkDestination){
            LkScreen(
                viewModel = hiltViewModel(),
                navController = navController,
            )
        }
    }
}
