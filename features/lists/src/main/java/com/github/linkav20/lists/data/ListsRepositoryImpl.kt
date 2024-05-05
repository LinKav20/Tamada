package com.github.linkav20.lists.data

import android.util.Log
import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.entity.TaskEntity
import com.github.linkav20.lists.domain.repository.ListsRepository
import com.github.linkav20.network.data.api.EventApi
import com.github.linkav20.network.data.models.CommonGetListInfoIn
import com.github.linkav20.network.data.models.CommonGetListInfoOut
import com.github.linkav20.network.data.models.CommonGetPartyListsIn
import com.github.linkav20.network.data.models.CommonGetPartyListsOut
import com.github.linkav20.network.data.models.CommonTaskInfo
import com.github.linkav20.network.utils.RetrofitErrorHandler
import javax.inject.Inject

class ListsRepositoryImpl @Inject constructor(
    private val retrofitErrorHandler: RetrofitErrorHandler,
    private val eventApi: EventApi
) : ListsRepository {
    override suspend fun getLists(partyId: Long): List<ListEntity> = retrofitErrorHandler.apiCall {
        eventApi.getPartyLists(CommonGetPartyListsIn(partyId.toInt()))
    }?.map { it.toDomain() } ?: emptyList()

    override suspend fun getListById(listId: Long, partyId: Long): ListEntity =
        retrofitErrorHandler.apiCall {
            eventApi.getListInfo(
                CommonGetListInfoIn(
                    listID = listId.toInt(),
                    partyID = partyId.toInt(),
                )
            )
        }!!.toDomain()

    override suspend fun sendList(list: ListEntity) {
        Log.d("MY_", "Sended $list")
    }
}

private fun CommonGetPartyListsOut.toDomain() = ListEntity(
    id = id.toLong(),
    tasks = emptyList(),
    managersOnly = true,
    type = ListEntity.Type.EMPTY
)

private fun CommonGetListInfoOut.toDomain() = ListEntity(
    id = listID.toLong(),
    tasks = tasks?.map { it.toDomain() } ?: emptyList(),
    managersOnly = true,
    type = ListEntity.Type.EMPTY
)

private fun CommonTaskInfo.toDomain() = TaskEntity(
    id = taskID.toLong(),
    name = name,
    done = isDone
)
