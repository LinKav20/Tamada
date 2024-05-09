package com.github.linkav20.lists.presentation.main

import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.entity.TaskEntity
import java.lang.Error

data class ListsMainState(
    val loading: Boolean = false,
    val isManager: Boolean = false,
    val managersFilter: Boolean = false,
    val createdListId: Int? = null,
    val lists: List<ListEntity> = emptyList(),
    val error: Throwable? = null
) {
    fun filteredLists() = if (managersFilter) {
        lists.filter { it.managersOnly }
    } else {
        lists
    }
}
