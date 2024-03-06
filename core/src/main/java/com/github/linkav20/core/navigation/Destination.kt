package com.github.linkav20.core.navigation

import android.transition.TransitionSet
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

interface Destination {
    fun route(): String

    val arguments: List<NamedNavArgument>
        get() = emptyList()

    val deepLinks: List<NavDeepLink>
        get() = emptyList()

    val transitionSet: TransitionSet?
        get() = null
}
