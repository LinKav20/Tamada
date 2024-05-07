package com.github.linkav20.lists.presentation.list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.ReactionStyle
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.lists.domain.entity.TaskEntity
import com.github.linkav20.lists.domain.usecase.CreateTasksUseCase
import com.github.linkav20.lists.domain.usecase.GetListByIdUseCase
import com.github.linkav20.lists.domain.usecase.UpdateListVisibilityUseCase
import com.github.linkav20.lists.domain.usecase.UpdateTaskDoneUseCase
import com.github.linkav20.lists.domain.usecase.UpdateTaskNameUseCase
import com.github.linkav20.lists.navigation.ListDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val EMPTY_TASK_ID = 0L

@HiltViewModel
class ListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getListByIdUseCase: GetListByIdUseCase,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    private val updateListVisibilityUseCase: UpdateListVisibilityUseCase,
    private val updateTaskNameUseCase: UpdateTaskNameUseCase,
    private val updateTaskDoneUseCase: UpdateTaskDoneUseCase,
    private val createTasksUseCase: CreateTasksUseCase,
    private val reactUseCase: ReactUseCase
) : ViewModel() {

    private val id = ListDestination.extractId(savedStateHandle)

    private val _state = MutableStateFlow(ListState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onAddNewPointClick() {
        addEmptyTask(EMPTY_TASK_ID.toInt())
    }

    fun onBackClick() = saveNotServerTasks()


    fun onFilterChanged(value: Boolean) {
        _state.update { it.copy(guestsAccessGranted = value) }
        updateListVisibility(value = value)
    }

    fun onNextClick(task: TaskEntity) {
        val list = state.value.list
        val index = list?.tasks?.indexOf(task) ?: return
        if (task.name.isNotEmpty()) {
            if (task.id == EMPTY_TASK_ID) {
                val newTask = list.tasks.find { it == task }?.copy(id = list.tasks.size.toLong())
                updateTaskInList(task, newTask)
            }
            val newIndex = index + 1
            addEmptyTask(newIndex)
        }
    }

    fun onValueChanged(task: TaskEntity, value: String) {
        val newTask = state.value.list?.tasks?.find { it == task }?.copy(name = value)
        updateTaskInList(task, newTask)
    }

    fun onFocusChanged(index: Int, task: TaskEntity) {
        _state.value.list?.tasks?.forEach {
            if (it.name.isEmpty()) {
                if (it.id == EMPTY_TASK_ID) {
                    onDeleteTaskClick(it)
                } else {
                    val newTask = if (task.id == EMPTY_TASK_ID) {
                        state.value.list!!.tasks.find { it == task }
                            ?.copy(id = state.value.list!!.tasks.size.toLong())
                    } else {
                        state.value.list!!.tasks.find { it == task }
                    }
                    updateTaskInList(task, newTask)
                }
            }
        }
        _state.update { it.copy(focusedItemPosition = index) }
    }

    fun onTaskClick(task: TaskEntity) {
        val newTask =
            state.value.list?.tasks?.find { it == task }?.copy(done = !task.done) ?: return
        updateTaskInList(task, newTask, false)
        if (newTask.isServer) {
            updateTaskDone(newTask)
        }
    }

    fun onDeleteTaskClick(task: TaskEntity) {
        if (state.value.list != null) {
            val newTasks = state.value.list!!.tasks.minus(task)
            val newList = state.value.list!!.copy(tasks = newTasks)
            _state.update { it.copy(list = newList) }
        }
    }

    private fun updateTaskInList(
        task: TaskEntity,
        newTask: TaskEntity?,
        updateName: Boolean = true
    ) {
        if (state.value.list != null && newTask != null) {
            val index = state.value.list!!.tasks.indexOf(task)
            val newTasks = state.value.list!!.tasks.minus(task).toMutableList()
            newTasks.add(index, newTask)
            val newList = state.value.list!!.copy(tasks = newTasks)
            _state.update { it.copy(list = newList) }
            if (task.isServer && updateName) updateTaskName(newTask)
        }
    }

    private fun addEmptyTask(position: Int) {
        val newTasks = state.value.list!!.tasks.toMutableList()
        newTasks.add(position, TaskEntity(EMPTY_TASK_ID, ""))
        val newList = state.value.list!!.copy(tasks = newTasks)
        _state.update {
            it.copy(
                list = newList,
                newTaskPosition = position,
                focusedItemPosition = position
            )
        }
    }

    private fun loadData() = viewModelScope.launch {
        try {
            _state.update { it.copy(loading = true) }
            val partyId = getPartyIdUseCase.invoke() ?: return@launch
            val list = getListByIdUseCase.invoke(
                id = id,
                partyId = partyId
            )
            val role = getRoleUseCase.invoke()
            _state.update {
                it.copy(
                    list = list,
                    guestsAccessGranted = !(list?.managersOnly ?: false),
                    isManager = role == UserRole.MANAGER || role == UserRole.CREATOR
                )
            }
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }

    private fun updateListVisibility(value: Boolean) = viewModelScope.launch {
        try {
            updateListVisibilityUseCase.invoke(
                listId = id,
                managersOnly = value
            )
        } catch (e: Exception) {
            reactUseCase.invoke(
                title = "Не получилось обновить видимость списка",
                subtitle = e.message,
                style = ReactionStyle.ERROR
            )
            _state.update { it.copy(guestsAccessGranted = !value) }
        }
    }

    private fun updateTaskName(task: TaskEntity) = viewModelScope.launch {
        try {
            if (task.name.isNotEmpty()) {
                updateTaskNameUseCase.invoke(task)
            }
        } catch (e: Exception) {
            reactUseCase.invoke(
                title = "Не получилось обновить содержание пункта",
                subtitle = e.message,
                style = ReactionStyle.ERROR
            )
        }
    }

    private fun updateTaskDone(task: TaskEntity) = viewModelScope.launch {
        try {
            if (task.isServer) updateTaskDoneUseCase.invoke(task)
        } catch (e: Exception) {
            reactUseCase.invoke(
                title = "Не получилось обновить выполнение пункта",
                subtitle = e.message,
                style = ReactionStyle.ERROR
            )
        }
    }

    private fun saveNotServerTasks() = viewModelScope.launch {
        try {
            createTasksUseCase.invoke(
                listId = id,
                tasks = state.value.list?.tasks ?: emptyList()
            )
            _state.update { it.copy(action = ListState.Action.BACK) }
        } catch (e: Exception) {
            reactUseCase.invoke(
                title = "Не получилось сохранить все новые задания",
                subtitle = e.message,
                style = ReactionStyle.ERROR
            )
        }
    }
}