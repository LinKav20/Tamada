package com.github.linkav20.info.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.navigation.common.CreatePartyDestination
import com.github.linkav20.core.navigation.common.InfoTabDestination
import com.github.linkav20.core.navigation.composableRoute
import com.github.linkav20.info.presentation.create.CreatePartyScreen
import com.github.linkav20.info.presentation.info.PartyInfoScreen

fun NavGraphBuilder.infoGraph(navController: NavController, errorMapper: ErrorMapper) {
    navigation(
        startDestination = CreatePartyDestination.route(),
        route = CreatePartyDestination.route() + "graph",
    ) {
        composableRoute(CreatePartyDestination) {
            CreatePartyScreen(
                viewModel = hiltViewModel(),
                navController = navController,
            )
        }
    }
}

fun NavGraphBuilder.infoTabGraph(navController: NavController, errorMapper: ErrorMapper) {
    navigation(
        route = InfoTabDestination.route(),
        startDestination = InfoDestination.route()
    ) {
        composableRoute(InfoDestination) {
            PartyInfoScreen(
                viewModel = hiltViewModel(),
                navController = navController,
                errorMapper = errorMapper
            )
        }
    }
}
