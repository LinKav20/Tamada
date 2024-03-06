package com.github.linkav20.finance.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.navigation.common.ExpensesTabDestination
import com.github.linkav20.core.navigation.composableRoute
import com.github.linkav20.finance.presentation.main.MainFinanceScreen

fun NavGraphBuilder.financeTabGraph(navController: NavController, errorMapper: ErrorMapper) {
    navigation(
        route = ExpensesTabDestination.route(),
        startDestination = MainFinanceDestination.route(),
    ) {
        composableRoute(MainFinanceDestination) {
            MainFinanceScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}