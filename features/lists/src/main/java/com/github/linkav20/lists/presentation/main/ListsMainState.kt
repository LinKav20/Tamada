package com.github.linkav20.lists.presentation.main

import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.entity.TaskEntity

data class ListsMainState(
    val loading: Boolean = false,
    val managersFilter: Boolean = false,
    val lists: List<ListEntity> = emptyList()
) {
    fun filteredLists() = if (managersFilter) {
        lists.filter { it.managersOnly }
    } else {
        lists
    }
}
