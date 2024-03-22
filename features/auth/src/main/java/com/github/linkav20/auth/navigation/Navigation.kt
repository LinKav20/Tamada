package com.github.linkav20.auth.navigation

import androidx.compose.material.Text
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.github.linkav20.auth.presentation.login.LoginScreen
import com.github.linkav20.auth.presentation.main.AuthMainScreen
import com.github.linkav20.auth.presentation.signup.SignUpScreen
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.navigation.composableRoute

fun NavGraphBuilder.authGraph(navController: NavController, errorMapper: ErrorMapper) {
    navigation(
        startDestination = AuthMainScreenDestination.route(),
        route = AuthGraphDestination.route(),
    ) {
        composableRoute(AuthMainScreenDestination) {
            AuthMainScreen(
                viewModel = hiltViewModel(),
                navController = navController,
            )
        }
        composableRoute(LoginDestination) {
            LoginScreen(
                viewModel = hiltViewModel(),
                navController = navController,
            )
        }
        composableRoute(SignUpDestination) {
            SignUpScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}
