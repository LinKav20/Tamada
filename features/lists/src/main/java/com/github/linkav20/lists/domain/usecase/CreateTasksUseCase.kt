package com.github.linkav20.lists.domain.usecase

import com.github.linkav20.lists.domain.entity.TaskEntity
import com.github.linkav20.lists.domain.repository.ListsRepository
import javax.inject.Inject

class CreateTasksUseCase @Inject constructor(
    private val repository: ListsRepository
) {

    suspend fun invoke(listId: Long, tasks: List<TaskEntity>) {
        repository.createTask(
            listId = listId,
            tasks = tasks.filter { !it.isServer && it.name.isNotEmpty() }
        )
    }
}
