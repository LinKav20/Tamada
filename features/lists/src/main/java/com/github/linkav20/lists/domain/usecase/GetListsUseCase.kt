package com.github.linkav20.lists.domain.usecase

import com.github.linkav20.lists.domain.repository.ListsRepository
import javax.inject.Inject

class GetListsUseCase @Inject constructor(
    private val repository: ListsRepository
) {

    suspend fun invoke(partyId: Long) = repository.getLists(partyId)
}