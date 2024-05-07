package com.github.linkav20.lists.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.repository.ListsRepository
import javax.inject.Inject

class CreateListUseCase @Inject constructor(
    private val repository: ListsRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {

    suspend fun invoke(type: ListEntity.Type): Int? {
        val partyId = getPartyIdUseCase.invoke() ?: return null
        return repository.createList(partyId = partyId, type = type)
    }
}
