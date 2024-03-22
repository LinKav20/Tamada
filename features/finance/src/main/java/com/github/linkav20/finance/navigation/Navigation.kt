package com.github.linkav20.finance.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.github.linkav20.core.error.ErrorMapper
import com.github.linkav20.core.navigation.common.ExpensesTabDestination
import com.github.linkav20.core.navigation.composableRoute
import com.github.linkav20.finance.presentation.addexpense.AddExpenseDialog
import com.github.linkav20.finance.presentation.main.MainFinanceScreen
import com.github.linkav20.finance.presentation.myexpenses.MyExpensesScreen
import com.github.linkav20.finance.presentation.onboarding.OnboardingScreen
import com.github.linkav20.finance.presentation.progress.ProgressScreen
import com.github.linkav20.finance.presentation.step1.Step1Screen
import com.github.linkav20.finance.presentation.step2.Step2Screen
import com.github.linkav20.finance.presentation.step3.Step3Screen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

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
        composableRoute(Step1Destination) {
            Step1Screen(
                viewModel = hiltViewModel(),
                dialogViewModel = hiltViewModel(),
                navController = navController
            )
        }
        composableRoute(Step2Destination) {
            Step2Screen(
                viewModel = hiltViewModel(),
                dialogViewModel = hiltViewModel(),
                navController = navController
            )
        }
        composableRoute(OnboardingDestination) {
            OnboardingScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composableRoute(ProgressDestination) {
            ProgressScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
        composableRoute(MyExpensesDestination) {
            MyExpensesScreen(
                viewModel = hiltViewModel(),
                dialogViewModel = hiltViewModel(),
                navController = navController
            )
        }
        composableRoute(Step3Destination) {
            Step3Screen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}