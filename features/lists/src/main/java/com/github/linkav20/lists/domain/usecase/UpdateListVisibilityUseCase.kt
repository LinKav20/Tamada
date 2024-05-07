package com.github.linkav20.lists.domain.usecase

import com.github.linkav20.lists.domain.repository.ListsRepository
import javax.inject.Inject

class UpdateListVisibilityUseCase @Inject constructor(
    private val repository: ListsRepository
) {
    suspend fun invoke(listId: Long, managersOnly: Boolean) {
        repository.updateListVisibility(
            listId = listId,
            managersOnly = managersOnly
        )
    }
}
