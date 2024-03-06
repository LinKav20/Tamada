package com.github.linkav20.lists.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.navigation.common.ListsTabDestination
import com.github.linkav20.core.navigation.composableRoute
import com.github.linkav20.lists.presentation.list.ListScreen
import com.github.linkav20.lists.presentation.main.ListsMainScreen

fun NavGraphBuilder.listsTabGraph(navController: NavController, errorMapper: ErrorMapper) {
    navigation(
        route = ListsTabDestination.route(),
        startDestination = ListsMainDestination.route(),
    ) {
        composableRoute(ListsMainDestination) {
            ListsMainScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composableRoute(ListDestination) {
            ListScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composable("ListsTabDestination2") {
            Text(
                modifier =
                Modifier.clickable {
                    navController.navigateUp()
                },
                text = "ListsTabDestination2",
            )
        }
    }
}
