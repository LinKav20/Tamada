package com.github.linkav20.lists.domain.usecase

import com.github.linkav20.lists.domain.entity.ListEntity
import javax.inject.Inject

class GetListsFullInfoUseCase @Inject constructor(
    private val getListsUseCase: GetListsUseCase,
    private val getListByIdUseCase: GetListByIdUseCase
) {

    suspend fun invoke(partyId: Long): List<ListEntity> {
        val listsIds = getListsUseCase.invoke(partyId = partyId)
        val lists = mutableListOf<ListEntity>()
        listsIds.forEach {
            val list = getListByIdUseCase.invoke(id = it.id, partyId = partyId)
            if (list != null) {
                lists.add(list)
            }
        }
        return lists
    }
}