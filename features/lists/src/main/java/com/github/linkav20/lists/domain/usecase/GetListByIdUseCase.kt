package com.github.linkav20.lists.domain.usecase

import com.github.linkav20.lists.domain.repository.ListsRepository
import javax.inject.Inject

class GetListByIdUseCase @Inject constructor(
    private val repository: ListsRepository
) {

    suspend fun invoke(id: Long, partyId: Long) = repository.getListById(
        listId = id,
        partyId = partyId
    )
}