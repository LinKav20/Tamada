package com.github.linkav20.lists.domain.usecase

import com.github.linkav20.lists.domain.entity.TaskEntity
import com.github.linkav20.lists.domain.repository.ListsRepository
import javax.inject.Inject

class UpdateTaskDoneUseCase @Inject constructor(
    private val repository: ListsRepository
) {

    suspend fun invoke(taskEntity: TaskEntity) {
        repository.updateTaskState(taskEntity)
    }
}
