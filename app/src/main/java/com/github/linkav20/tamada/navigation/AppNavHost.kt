package com.github.linkav20.tamada.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.github.linkav20.auth.navigation.authGraph
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.finance.navigation.financeTabGraph
import com.github.linkav20.guests.navigation.guestsTabGraph
import com.github.linkav20.home.navigation.homeGraph
import com.github.linkav20.info.navigation.infoGraph
import com.github.linkav20.info.navigation.infoTabGraph
import com.github.linkav20.lists.navigation.listsTabGraph

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    errorMapper: ErrorMapper,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        authGraph(
            navController = navController,
            errorMapper = errorMapper
        )
        homeGraph(
            navController = navController,
            errorMapper = errorMapper
        )
        infoGraph(
            navController = navController,
            errorMapper = errorMapper
        )
        infoTabGraph(
            navController = navController,
            errorMapper = errorMapper
        )
        listsTabGraph(
            navController = navController,
            errorMapper = errorMapper
        )
        guestsTabGraph(
            navController = navController,
            errorMapper = errorMapper
        )
        financeTabGraph(
            navController = navController,
            errorMapper = errorMapper
        )
    }
}
