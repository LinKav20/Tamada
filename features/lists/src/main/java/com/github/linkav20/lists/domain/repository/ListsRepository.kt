package com.github.linkav20.lists.domain.repository

import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.entity.TaskEntity

interface ListsRepository {

    suspend fun getLists(partyId: Long): List<ListEntity>

    suspend fun getListById(listId: Long, partyId: Long): ListEntity?

    suspend fun createList(partyId: Long, type: ListEntity.Type): Int?

    suspend fun createTask(listId: Long, tasks: List<TaskEntity>)

    suspend fun deleteList(partyId: Long, listId: Long)

    suspend fun updateListVisibility(listId: Long, managersOnly: Boolean)

    suspend fun updateTaskName(taskEntity: TaskEntity)

    suspend fun updateTaskState(taskEntity: TaskEntity)
}