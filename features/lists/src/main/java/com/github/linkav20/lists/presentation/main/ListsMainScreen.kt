package com.github.linkav20.lists.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaFullscreenLoader
import com.github.linkav20.coreui.ui.TamadaRadioButton
import com.github.linkav20.coreui.ui.TamadaSwitch
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.coreui.utils.getSecondaryColor
import com.github.linkav20.lists.R
import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.entity.TaskEntity
import com.github.linkav20.lists.navigation.ListDestination
import com.github.linkav20.lists.presentation.shared.Filter
import com.github.linkav20.lists.presentation.shared.getListTitle
import com.github.linkav20.coreui.R as CoreR

private const val SHOW_TASKS = 6

@Composable
fun ListsMainScreen(
    viewModel: ListsMainViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

    Content(
        managersFilter = state.managersFilter,
        loading = state.loading,
        lists = state.filteredLists(),
        onFilterChange = viewModel::onFilterChange,
        onTaskClick = viewModel::onTaskClick,
        onListClick = { navController.navigate(ListDestination.createRoute(it)) },
        onBackClick = { navController.navigateUp() },
    )
}

@Composable
private fun Content(
    managersFilter: Boolean,
    loading: Boolean,
    lists: List<ListEntity>,
    onFilterChange: (Boolean) -> Unit,
    onTaskClick: (ListEntity, TaskEntity) -> Unit,
    onListClick: (Long) -> Unit,
    onBackClick: () -> Unit
) = Scaffold(
    backgroundColor = getBackgroundColor(scheme = ColorScheme.LISTS),
    topBar = {
        TamadaTopBar(
            colorScheme = ColorScheme.LISTS,
            title = stringResource(id = R.string.lists_main_title),
            onBackClick = onBackClick
        )
    }
) { paddings ->
    if (loading) {
        TamadaFullscreenLoader(scheme = ColorScheme.LISTS)
    } else {
        Column(
            modifier = Modifier
                .padding(paddings)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Filter(
                title = stringResource(id = R.string.lists_main_filter_only_for_managers),
                isSelected = managersFilter,
                onFilterChange = onFilterChange
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { ListsHint(lists.isEmpty()) }
                items(lists) {
                    ListItem(
                        modifier = Modifier.clickable { onListClick(it.id) },
                        listEntity = it,
                        onTaskClick = onTaskClick
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
private fun ListsHint(
    isEmpty: Boolean
) = TamadaCard(
    colorScheme = ColorScheme.LISTS
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.lists_main_create_list_title),
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader,
        )
        Text(
            text = stringResource(id = R.string.lists_main_create_list_description),
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain,
        )
        Spacer(modifier = Modifier.height(8.dp))
        ListDisclaimer(
            isEmpty = isEmpty,
            title = stringResource(id = R.string.lists_main_empty_list_title),
            subtitle = stringResource(id = R.string.lists_main_empty_list_description)
        )
        ListDisclaimer(
            isEmpty = isEmpty,
            title = stringResource(id = R.string.lists_main_todo_list_title),
            subtitle = stringResource(id = R.string.lists_main_todo_list_description)
        )
        ListDisclaimer(
            isEmpty = isEmpty,
            title = stringResource(id = R.string.lists_main_buying_list_title),
            subtitle = stringResource(id = R.string.lists_main_buying_list_description)
        )
        ListDisclaimer(
            isEmpty = isEmpty,
            title = stringResource(id = R.string.lists_main_wishlist_title),
            subtitle = stringResource(id = R.string.lists_main_wishlist_description)
        )
    }
}

@Composable
private fun ListDisclaimer(
    isEmpty: Boolean,
    title: String,
    subtitle: String
) = Column(
    modifier = Modifier
        .then(if (!isEmpty) Modifier.clickable { } else Modifier)
        .fillMaxWidth()
        .clip(TamadaTheme.shapes.mediumSmall)
        .background(getSecondaryColor(scheme = ColorScheme.LISTS))
        .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    Text(
        text = title,
        style = TamadaTheme.typography.head,
        color = TamadaTheme.colors.textHeader,
    )
    if (isEmpty) {
        Text(
            text = subtitle,
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain,
        )
    }
}

@Composable
private fun ListItem(
    modifier: Modifier = Modifier,
    listEntity: ListEntity,
    onTaskClick: (ListEntity, TaskEntity) -> Unit,
) = TamadaCard(
    modifier = modifier,
    colorScheme = ColorScheme.LISTS
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = getListTitle(listEntity.type),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain,
            )
            Icon(
                painter = if (true) {
                    painterResource(id = CoreR.drawable.lock_icon)
                } else {
                    painterResource(id = CoreR.drawable.baseline_lock_open_24)
                },
                tint = TamadaTheme.colors.textMain,
                contentDescription = ""
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            listEntity.tasks.slice(0 until SHOW_TASKS).forEach {
                TaskItem(
                    task = it,
                    onClick = { onTaskClick(listEntity, it) }
                )
            }
        }
        if (listEntity.tasks.size > SHOW_TASKS) {
            Text(
                text = stringResource(
                    R.string.lists_main_more_tasks,
                    listEntity.tasks.size - SHOW_TASKS
                ),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain,
            )
        }
    }
}

@Composable
private fun TaskItem(
    task: TaskEntity,
    onClick: () -> Unit
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() },
    verticalAlignment = Alignment.CenterVertically
) {
    TamadaRadioButton(
        colorScheme = ColorScheme.LISTS,
        selected = task.done,
        onClick = onClick
    )
    Text(
        text = task.name,
        style = TamadaTheme.typography.head,
        color = TamadaTheme.colors.textHeader,
    )
}
