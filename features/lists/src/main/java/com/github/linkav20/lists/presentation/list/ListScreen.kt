package com.github.linkav20.lists.presentation.list

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaGradientDisclaimer
import com.github.linkav20.coreui.ui.TamadaRadioButton
import com.github.linkav20.coreui.ui.TamadaTextFiled
import com.github.linkav20.coreui.ui.TamadaTopBar
import com.github.linkav20.coreui.ui.TextFiledType
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getBackgroundColor
import com.github.linkav20.coreui.utils.getSecondaryColor
import com.github.linkav20.lists.R
import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.entity.TaskEntity
import com.github.linkav20.lists.presentation.shared.Filter
import com.github.linkav20.lists.presentation.shared.getListTitle
import com.github.linkav20.coreui.R as CoreR

@Composable
fun ListScreen(
    viewModel: ListViewModel,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value
    Content(
        focusedItemPosition = state.focusedItemPosition,
        guestsAccessGranted = state.guestsAccessGranted,
        listEntity = state.list,
        doneTasks = state.getDoneTasks(),
        notDoneTasks = state.getNotDoneTasks(),
        isManager = state.isManager,
        onBackClick = viewModel::onBackClick,
        onTaskClick = viewModel::onTaskClick,
        onDeleteTaskClick = viewModel::onDeleteTaskClick,
        onAddNewPointClick = viewModel::onAddNewPointClick,
        onValueChanged = viewModel::onValueChanged,
        onNextClick = viewModel::onNextClick,
        onFocusChanged = viewModel::onFocusChanged,
        onFilterChanged = viewModel::onFilterChanged
    )

    LaunchedEffect(state.action) {
        if (state.action == ListState.Action.BACK) {
            navController.navigateUp()
        }
    }

    BackHandler {
        viewModel.onBackClick()
    }
}

@Composable
private fun Content(
    focusedItemPosition: Int?,
    listEntity: ListEntity?,
    doneTasks: List<TaskEntity>,
    notDoneTasks: List<TaskEntity>,
    isManager: Boolean,
    guestsAccessGranted: Boolean,
    onBackClick: () -> Unit,
    onTaskClick: (TaskEntity) -> Unit,
    onDeleteTaskClick: (TaskEntity) -> Unit,
    onAddNewPointClick: () -> Unit,
    onValueChanged: (TaskEntity, String) -> Unit,
    onNextClick: (TaskEntity) -> Unit,
    onFocusChanged: (Int, TaskEntity) -> Unit,
    onFilterChanged: (Boolean) -> Unit,
) = Scaffold(
    backgroundColor = getBackgroundColor(scheme = ColorScheme.LISTS),
    topBar = {
        TamadaTopBar(
            colorScheme = ColorScheme.LISTS,
            title = if (listEntity == null) {
                stringResource(id = R.string.list_screen_default_title)
            } else {
                getListTitle(listEntity.type)
            },
            onBackClick = onBackClick
        )
    },
    bottomBar = {
        TamadaCard(
            modifier = Modifier.padding(16.dp),
            colorScheme = ColorScheme.LISTS
        ) {
            TamadaButton(
                onClick = onAddNewPointClick,
                title = stringResource(id = R.string.list_screen_add_button),
                type = ButtonType.OUTLINE,
                colorScheme = ColorScheme.LISTS
            )
        }
    }
) { paddings ->
    Column(
        modifier = Modifier
            .padding(paddings)
            .padding(16.dp)
    ) {
        if (isManager) {
            Filter(
                modifier = Modifier.padding(vertical = 16.dp),
                title = stringResource(id = R.string.list_screen_access_to_guests),
                isSelected = guestsAccessGranted,
                onFilterChange = onFilterChanged
            )
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            if (listEntity != null) {
                itemsIndexed(notDoneTasks) { index, task ->
                    if (index == focusedItemPosition) {
                        FocusedEditingItem(
                            modifier = Modifier,
                            task = task,
                            onValueChanged = onValueChanged,
                            onDeleteClick = { onDeleteTaskClick(task) },
                            onRadioButtonClick = onTaskClick,
                            onNextClick = { onNextClick(task) },
                            onFocusChanged = { onFocusChanged(index, task) }
                        )
                    } else {
                        EditingItem(
                            modifier = Modifier,
                            task = task,
                            onValueChanged = onValueChanged,
                            onDeleteClick = { onDeleteTaskClick(task) },
                            onRadioButtonClick = onTaskClick,
                            onNextClick = { onNextClick(task) },
                            onFocusChanged = { onFocusChanged(index, task) }
                        )
                    }
                }
                if (doneTasks.isNotEmpty()) {
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                    item {
                        Divider(
                            color = getSecondaryColor(scheme = ColorScheme.LISTS),
                            thickness = 1.dp
                        )
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                    item {
                        TamadaGradientDisclaimer(
                            colorScheme = ColorScheme.FINANCE
                        ) {
                            Text(
                                text = stringResource(id = R.string.list_screen_finance_disclaimer),
                                color = TamadaTheme.colors.textWhite,
                                style = TamadaTheme.typography.caption
                            )
                        }
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                    items(doneTasks) {
                        TaskItem(
                            taskEntity = it,
                            onTaskClick = onTaskClick,
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun TaskItem(
    taskEntity: TaskEntity,
    onTaskClick: (TaskEntity) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClick(taskEntity) }
            .padding(horizontal = 10.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = CoreR.drawable.outline_check_circle_outline_24),
            tint = TamadaTheme.colors.textMain,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = taskEntity.name,
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader,
            textDecoration = TextDecoration.LineThrough
        )
    }
}

@Composable
private fun EditingItem(
    modifier: Modifier = Modifier,
    task: TaskEntity,
    onValueChanged: (TaskEntity, String) -> Unit,
    onDeleteClick: () -> Unit,
    onRadioButtonClick: (TaskEntity) -> Unit,
    onNextClick: () -> Unit,
    onFocusChanged: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TamadaCard(
            modifier = Modifier.weight(1f),
            colorScheme = ColorScheme.LISTS
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TamadaRadioButton(
                    colorScheme = ColorScheme.LISTS,
                    selected = task.done,
                    onClick = { onRadioButtonClick(task) }
                )
                TamadaTextFiled(
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        onFocusChanged()
                                    }
                                }
                            }
                        },
                    modifier = modifier,
                    onValueChange = { onValueChanged(task, it) },
                    type = TextFiledType.PRIMARY,
                    value = task.name,
                    colorScheme = ColorScheme.LISTS,
                    textStyle = TamadaTheme.typography.head,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { onNextClick() })
                )
            }
        }
        TamadaButton(
            colorScheme = ColorScheme.LISTS,
            iconPainter = painterResource(id = CoreR.drawable.delete_icon),
            backgroundColor = TamadaTheme.colors.textWhite,
            type = ButtonType.SECONDARY,
            elevation = 20.dp,
            onClick = onDeleteClick
        )
    }
}

@Composable
private fun FocusedEditingItem(
    modifier: Modifier = Modifier,
    task: TaskEntity,
    onValueChanged: (TaskEntity, String) -> Unit,
    onDeleteClick: () -> Unit,
    onRadioButtonClick: (TaskEntity) -> Unit,
    onNextClick: () -> Unit,
    onFocusChanged: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TamadaCard(
            modifier = Modifier.weight(1f),
            colorScheme = ColorScheme.LISTS
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TamadaRadioButton(
                    colorScheme = ColorScheme.LISTS,
                    selected = task.done,
                    onClick = { onRadioButtonClick(task) }
                )
                TamadaTextFiled(
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        onFocusChanged()
                                    }
                                }
                            }
                        },
                    modifier = modifier
                        .focusRequester(focusRequester),
                    onValueChange = { onValueChanged(task, it) },
                    type = TextFiledType.PRIMARY,
                    value = task.name,
                    colorScheme = ColorScheme.LISTS,
                    textStyle = TamadaTheme.typography.head,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { onNextClick() })
                )
            }
        }
        TamadaButton(
            colorScheme = ColorScheme.LISTS,
            iconPainter = painterResource(id = CoreR.drawable.delete_icon),
            backgroundColor = TamadaTheme.colors.textWhite,
            type = ButtonType.SECONDARY,
            elevation = 20.dp,
            onClick = onDeleteClick
        )
    }
    LaunchedEffect(Unit) { focusRequester.requestFocus() }
}
