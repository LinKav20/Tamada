package com.github.linkav20.lists.domain.usecase

import com.github.linkav20.lists.domain.repository.ListsRepository
import javax.inject.Inject

class AddTasksToListUseCase @Inject constructor(
    private val repository: ListsRepository
) {
}
