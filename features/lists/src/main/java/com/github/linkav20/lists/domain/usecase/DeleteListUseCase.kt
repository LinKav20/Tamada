package com.github.linkav20.lists.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.lists.domain.repository.ListsRepository
import javax.inject.Inject

class DeleteListUseCase @Inject constructor(
    private val repository: ListsRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {
    suspend fun invoke(listId: Long) {
        val partyId = getPartyIdUseCase.invoke() ?: return
        repository.deleteList(
            listId = listId,
            partyId = partyId
        )
    }
}
