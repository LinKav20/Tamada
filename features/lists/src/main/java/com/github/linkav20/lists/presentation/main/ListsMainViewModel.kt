package com.github.linkav20.lists.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.entity.TaskEntity
import com.github.linkav20.lists.domain.usecase.GetListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsMainViewModel @Inject constructor(
    private val getListsUseCase: GetListsUseCase,
    private val getPartyIdUseCase: GetPartyIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListsMainState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun onFilterChange(value: Boolean) {
        _state.update { it.copy(managersFilter = value) }
    }

    fun onTaskClick(list: ListEntity, task: TaskEntity) {
        val newList = updateItemInList(list, task)
        if (newList != null) {
            val index = state.value.lists.indexOf(list)
            val newLists = state.value.lists.minus(list).toMutableList()
            newLists.add(index, newList)
            _state.update { it.copy(lists = newLists) }
        }
    }

    private fun updateItemInList(list: ListEntity, task: TaskEntity): ListEntity? {
        val newTask = list.tasks.find { it == task }?.copy(done = !task.done)
        if (newTask != null) {
            val index = list.tasks.indexOf(task)
            val newTasks = list.tasks.minus(task).toMutableList()
            newTasks.add(index, newTask)
            return list.copy(tasks = newTasks)
        }
        return null
    }

    private fun loadData() = viewModelScope.launch {
        val lists = getListsUseCase.invoke(1)
        val id = getPartyIdUseCase.invoke()
        Log.d("MY_", "Lists $id")
        _state.update { it.copy(lists = lists) }
    }
}