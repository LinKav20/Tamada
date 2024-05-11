package com.github.linkav20.lists.presentation.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.linkav20.core.domain.entity.UserRole
import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.core.domain.usecase.GetPartyNameUseCase
import com.github.linkav20.core.domain.usecase.GetRoleUseCase
import com.github.linkav20.core.notification.ReactUseCase
import com.github.linkav20.lists.R
import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.entity.TaskEntity
import com.github.linkav20.lists.domain.usecase.CreateListUseCase
import com.github.linkav20.lists.domain.usecase.GetListByIdUseCase
import com.github.linkav20.lists.domain.usecase.GetListsFullInfoUseCase
import com.github.linkav20.lists.domain.usecase.GetListsUseCase
import com.github.linkav20.lists.domain.usecase.NotifyListUserUseCase
import com.github.linkav20.lists.domain.usecase.UpdateTaskDoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsMainViewModel @Inject constructor(
    private val getUserRoleUseCase: GetRoleUseCase,
    private val getPartyIdUseCase: GetPartyIdUseCase,
    private val getListsFullInfoUseCase: GetListsFullInfoUseCase,
    private val updateTaskDoneUseCase: UpdateTaskDoneUseCase,
    private val createListUseCase: CreateListUseCase,
    private val reactUseCase: ReactUseCase,
    private val notifyListUserUseCase: NotifyListUserUseCase,
    private val userInformationRepository: UserInformationRepository,
    private val getPartyNameUseCase: GetPartyNameUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(ListsMainState())
    val state = _state.asStateFlow()

    fun onStart() = loadData()

    fun onRetry() {
        _state.update { it.copy(error = null) }
        loadData()
    }

    fun onCreateList(type: ListEntity.Type) = createList(type)

    fun onFilterChange(value: Boolean) {
        _state.update { it.copy(managersFilter = value) }
    }

    fun nullifyCreatedListId() = _state.update { it.copy(createdListId = null) }

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
            updateTaskOnServer(newTask)
            sendNotifications(list.managersOnly, newTask)
            return list.copy(tasks = newTasks)
        }
        return null
    }

    private fun updateTaskOnServer(task: TaskEntity) = viewModelScope.launch {
        try {
            updateTaskDoneUseCase.invoke(task)
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        }
    }

    private fun loadData() {
        loadRole()
        loadLists()
    }

    private fun loadLists() = viewModelScope.launch {
        try {
            _state.update { it.copy(loading = true) }
            val id = getPartyIdUseCase.invoke() ?: return@launch
            val lists = getListsFullInfoUseCase.invoke(partyId = id)
            _state.update {
                it.copy(
                    lists = lists,
                )
            }
        } catch (e: Exception) {
            _state.update { it.copy(error = e) }
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }

    private fun loadRole() = viewModelScope.launch {
        try {
            val role = getUserRoleUseCase.invoke()
            _state.update {
                it.copy(
                    isManager = role == UserRole.MANAGER || role == UserRole.CREATOR
                )
            }
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        }
    }

    private fun createList(type: ListEntity.Type) = viewModelScope.launch {
        try {
            _state.update { it.copy(loading = true) }
            val id = createListUseCase.invoke(type)
            _state.update { it.copy(createdListId = id) }
        } catch (e: Exception) {
            reactUseCase.invoke(e)
        } finally {
            _state.update { it.copy(loading = false) }
        }
    }

    private fun sendNotifications(isManagers: Boolean, task: TaskEntity) = viewModelScope.launch {
        try {
            if (task.done) {
                notifyListUserUseCase.invoke(
                    forManagers = isManagers,
                    title = context.getString(R.string.list_screen_notify_task_title),
                    subtitle = context.getString(
                        R.string.list_screen_notify_task_subtitle,
                        userInformationRepository.login,
                        task.name,
                        getPartyNameUseCase.invoke() ?: ""
                    )
                )
            }
        } catch (e: Exception) {

        }
    }
}