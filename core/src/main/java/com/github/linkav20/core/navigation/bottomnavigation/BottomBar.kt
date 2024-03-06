package com.github.linkav20.core.navigation.bottomnavigation

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.github.linkav20.core.domain.entity.BottomNavigationItem
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.utils.getPrimaryColor

@Composable
fun BottomBar(
    navController: NavController,
    bottomNavigationEntries: List<BottomNavigationItem>,
    isFirstTabLoad: (BottomNavigationItem) -> Boolean
) {
    var showBottomNav by rememberSaveable { mutableStateOf(false) }

    BottomNavigation(
        modifier = if (showBottomNav) Modifier else Modifier.size(0.dp),
        backgroundColor = TamadaTheme.colors.backgroundPrimary,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        var isShow = false
        bottomNavigationEntries.forEachIndexed { index, bottomEntry ->
            val isSelected =
                navBackStackEntry?.destination?.hierarchy?.any {
                    it.route == bottomEntry.destination.route()
                } == true
            isShow = isShow || isSelected
            BottomNavigationItem(
                selected = isSelected,
                icon = {
                    Icon(
                        painter = painterResource(bottomEntry.drawableId),
                        contentDescription = "",
                    )
                },
                label = {
                    //if (isSelected) {
                    Text(
                        text = stringResource(id = bottomEntry.labelId),
                        color = if (isSelected) {
                            getPrimaryColor(scheme = bottomEntry.colorScheme)
                        } else {
                            TamadaTheme.colors.textMain
                        },
                    )
                    // }
                },
                onClick = {
                    navController.navigate(bottomEntry.destination.route()) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = !isFirstTabLoad(bottomEntry)
                        launchSingleTop = false
                    }
                },
                selectedContentColor = getPrimaryColor(scheme = bottomEntry.colorScheme),
                unselectedContentColor = TamadaTheme.colors.textMain,
            )
        }
        showBottomNav = isShow
    }
}
