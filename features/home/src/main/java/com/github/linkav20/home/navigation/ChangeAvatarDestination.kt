package com.github.linkav20.home.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.github.linkav20.core.navigation.Destination

object ChangeAvatarDestination : Destination {
    private const val ROUTE = "change_avatar"
    private const val ID = "avatar_id"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(ID) {
            type = NavType.IntType
        }
    )

    override fun route(): String = "$ROUTE/{$ID}"

    fun createRoute(avatar: Int) = "$ROUTE/$avatar"

    fun extractId(savedStateHandle: SavedStateHandle): Int = savedStateHandle[ID]!!

}