package com.github.linkav20.lists.presentation.list

import com.github.linkav20.lists.domain.entity.ListEntity

data class ListState(
    val loading: Boolean = false,
    val list: ListEntity? = null,
    val isManager: Boolean = false,
    val guestsAccessGranted: Boolean = false,
    val newTaskPosition: Int? = null,
    val focusedItemPosition: Int? = null,
    val action: Action? = null,
    val error: Throwable? = null
) {
    enum class Action { BACK }

    fun getDoneTasks() = list?.tasks?.filter { it.done } ?: emptyList()

    fun getNotDoneTasks() = list?.tasks?.filter { !it.done } ?: emptyList()
}