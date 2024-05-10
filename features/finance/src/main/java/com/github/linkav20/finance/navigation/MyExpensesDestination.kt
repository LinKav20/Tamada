package com.github.linkav20.finance.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.github.linkav20.core.navigation.Destination

object MyExpensesDestination : Destination {
    private const val ROUTE = "my_expenses_list"

    private const val STEP = "step"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(STEP) {
            type = NavType.IntType
        }
    )

    override fun route(): String = "${ROUTE}/{$STEP}"

    fun createRoute(step: Int) = "${ROUTE}/$step"

    fun extractStep(savedStateHandle: SavedStateHandle): Int = savedStateHandle[STEP]!!
}
