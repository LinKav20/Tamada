package com.github.linkav20.lists.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.github.linkav20.core.navigation.Destination

object ListDestination : Destination {
    const val ROUTE = "additional_receiving"
    const val ARG_ID = "id"

    override val arguments = listOf(
        navArgument(ARG_ID) { type = NavType.LongType },
    )

    override fun route() = "$ROUTE/{$ARG_ID}"
    fun createRoute(id: Long) = "$ROUTE/$id"
    fun extractId(savedStateHandle: SavedStateHandle): Long = savedStateHandle[ARG_ID]!!
}