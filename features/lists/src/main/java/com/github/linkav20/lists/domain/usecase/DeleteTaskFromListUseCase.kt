package com.github.linkav20.lists.domain.usecase

import com.github.linkav20.lists.domain.repository.ListsRepository
import javax.inject.Inject

class DeleteTaskFromListUseCase @Inject constructor(
    private val repository: ListsRepository
) {

    suspend fun invoke(listId: Long, taskId: Long) = repository.deleteTask(
        listId = listId,
        taskId = taskId
    )
}

