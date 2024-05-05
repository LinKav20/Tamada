package com.github.linkav20.lists.domain.repository

import com.github.linkav20.lists.domain.entity.ListEntity

interface ListsRepository {

    suspend fun getLists(partyId: Long): List<ListEntity>

    suspend fun getListById(listId: Long, partyId: Long): ListEntity

    suspend fun sendList(list: ListEntity)
}