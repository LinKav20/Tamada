package com.github.linkav20.core.domain.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.github.linkav20.core.navigation.Destination
import com.github.linkav20.core.navigation.common.ExpensesTabDestination
import com.github.linkav20.core.navigation.common.GuestsTabDestination
import com.github.linkav20.core.navigation.common.InfoTabDestination
import com.github.linkav20.core.navigation.common.ListsTabDestination
import com.github.linkav20.coreui.R
import com.github.linkav20.coreui.utils.ColorScheme

sealed class BottomNavigationItem(
    val destination: Destination,
    @StringRes val labelId: Int,
    @DrawableRes val drawableId: Int,
    val colorScheme: ColorScheme,
    val hasUpdates: Boolean = false,
)

object InfoBottomNavigationItem : BottomNavigationItem(
    destination = InfoTabDestination,
    colorScheme = ColorScheme.MAIN,
    drawableId = R.drawable.info_bottom_icon,
    labelId = R.string.bottom_bar_navigation_info,
)

object GuestsBottomNavigationItem : BottomNavigationItem(
    destination = GuestsTabDestination,
    colorScheme = ColorScheme.GUESTS,
    drawableId = R.drawable.guests_bottom_icon,
    labelId = R.string.bottom_bar_navigation_guests,
)

object ListsBottomNavigationItem : BottomNavigationItem(
    destination = ListsTabDestination,
    colorScheme = ColorScheme.LISTS,
    drawableId = R.drawable.lists_bottom_icon,
    labelId = R.string.bottom_bar_navigation_lists,
)

object ExpensesBottomNavigationItem : BottomNavigationItem(
    destination = ExpensesTabDestination,
    colorScheme = ColorScheme.FINANCE,
    drawableId = R.drawable.finance_bottom_icon,
    labelId = R.string.bottom_bar_navigation_expenses,
)
