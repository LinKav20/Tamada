package com.github.linkav20.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.composableRoute(
    destination: Destination,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.route(),
        arguments = destination.arguments,
        deepLinks = destination.deepLinks,
        content = content
    )
}