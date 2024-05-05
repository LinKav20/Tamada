package com.github.linkav20.lists.presentation.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import com.github.linkav20.lists.domain.entity.TaskEntity
import com.github.linkav20.lists.domain.usecase.GetListByIdUseCase
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
    private val getRoleUseCase: GetRoleUseCase
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

    fun onFilterChanged(value: Boolean) = _state.update { it.copy(guestsAccessGranted = value) }

    fun onNextClick(task: TaskEntity) {
        val list = state.value.list
        val index = list?.tasks?.indexOf(task) ?: return
        if (task.name.isNotEmpty() && list != null) {
            if (task.id == EMPTY_TASK_ID) {
                // createTask() TODO
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
                if (it.id == EMPTY_TASK_ID.toLong()) {
                    onDeleteTaskClick(it)
                } else {
                    // createTask() TODO
                    val newTask = state.value.list!!.tasks.find { it == task }
                        ?.copy(id = state.value.list!!.tasks.size.toLong())
                    updateTaskInList(task, newTask)
                }
            }
        }
        _state.update { it.copy(focusedItemPosition = index) }
    }

    fun onTaskClick(task: TaskEntity) {
        val newTask = state.value.list?.tasks?.find { it == task }?.copy(done = !task.done)
        updateTaskInList(task, newTask)
    }

    fun onDeleteTaskClick(task: TaskEntity) {
        if (state.value.list != null) {
            val newTasks = state.value.list!!.tasks.minus(task)
            val newList = state.value.list!!.copy(tasks = newTasks)
            _state.update { it.copy(list = newList) }
            //  TODO server update
        }
    }

    private fun updateTaskInList(task: TaskEntity, newTask: TaskEntity?) {
        if (state.value.list != null && newTask != null) {
            val index = state.value.list!!.tasks.indexOf(task)
            val newTasks = state.value.list!!.tasks.minus(task).toMutableList()
            newTasks.add(index, newTask)
            val newList = state.value.list!!.copy(tasks = newTasks)
            _state.update { it.copy(list = newList) }
            //  TODO server update
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
        val partyId = getPartyIdUseCase.invoke() ?: return@launch
        val list = getListByIdUseCase.invoke(
            id = id,
            partyId = partyId
        )
        val role = getRoleUseCase.invoke()
        _state.update { it.copy(list = list, isManager = role == UserRole.MANAGER) }
    }

    private fun createTask() = viewModelScope.launch {
        //go to netwerk
    }
}