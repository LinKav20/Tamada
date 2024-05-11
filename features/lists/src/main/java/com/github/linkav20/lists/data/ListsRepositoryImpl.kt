package com.github.linkav20.lists.data

import android.util.Log
import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.entity.TaskEntity
import com.github.linkav20.lists.domain.repository.ListsRepository
import com.github.linkav20.network.data.api.EventApi
import com.github.linkav20.network.data.models.CommonAddListToEventIn
import com.github.linkav20.network.data.models.CommonAddTasksToListIn
import com.github.linkav20.network.data.models.CommonDeleteListIn
import com.github.linkav20.network.data.models.CommonDeleteTaskFromListIn
import com.github.linkav20.network.data.models.CommonGetListInfoIn
import com.github.linkav20.network.data.models.CommonGetListInfoOut
import com.github.linkav20.network.data.models.CommonGetPartyListsIn
import com.github.linkav20.network.data.models.CommonGetPartyListsOut
import com.github.linkav20.network.data.models.CommonTaskCreateIn
import com.github.linkav20.network.data.models.CommonTaskInfo
import com.github.linkav20.network.data.models.CommonUpdateListVisibilityIn
import com.github.linkav20.network.data.models.CommonUpdateTaskNameIn
import com.github.linkav20.network.data.models.CommonUpdateTaskStatusIn
import com.github.linkav20.network.utils.RetrofitErrorHandler
import javax.inject.Inject

class ListsRepositoryImpl @Inject constructor(
    private val retrofitErrorHandler: RetrofitErrorHandler,
    private val eventApi: EventApi
) : ListsRepository {
    override suspend fun getLists(partyId: Long): List<ListEntity> = retrofitErrorHandler.apiCall {
        eventApi.getPartyLists(CommonGetPartyListsIn(partyId.toInt()))
    }?.map { it.toDomain() } ?: emptyList()

    override suspend fun getListById(listId: Long, partyId: Long): ListEntity? =
        retrofitErrorHandler.apiCall {
            eventApi.getListInfo(
                CommonGetListInfoIn(
                    listID = listId.toInt(),
                    partyID = partyId.toInt(),
                )
            )
        }?.toDomain()

    override suspend fun createList(partyId: Long, type: ListEntity.Type): Int? =
        retrofitErrorHandler.apiCall {
            eventApi.createList(
                CommonAddListToEventIn(
                    partyID = partyId.toInt(),
                    name = when (type) {
                        ListEntity.Type.EMPTY -> CommonAddListToEventIn.Name.eMPTY
                        ListEntity.Type.BUY -> CommonAddListToEventIn.Name.bUY
                        ListEntity.Type.WISHLIST -> CommonAddListToEventIn.Name.wISHLIST
                        ListEntity.Type.TODO -> CommonAddListToEventIn.Name.tODO
                    }
                )
            )
        }?.listID

    override suspend fun createTask(listId: Long, tasks: List<TaskEntity>) {
        retrofitErrorHandler.apiCall {
            eventApi.createTasks(
                CommonAddTasksToListIn(
                    listID = listId.toInt(),
                    tasks = tasks.map { CommonTaskCreateIn(name = it.name) }
                )
            )
        }
    }

    override suspend fun deleteList(partyId: Long, listId: Long) {
        retrofitErrorHandler.apiCall {
            eventApi.deleteList(
                CommonDeleteListIn(
                    listID = listId.toInt(),
                    partyID = partyId.toInt()
                )
            )
        }
    }

    override suspend fun updateListVisibility(listId: Long, managersOnly: Boolean) {
        retrofitErrorHandler.apiCall {
            eventApi.listVisibility(
                CommonUpdateListVisibilityIn(
                    listID = listId.toInt(),
                    isVisible = managersOnly
                )
            )
        }
    }

    override suspend fun updateTaskName(taskEntity: TaskEntity) {
        retrofitErrorHandler.apiCall {
            eventApi.updateTaskName(
                CommonUpdateTaskNameIn(
                    taskID = taskEntity.id.toInt(),
                    newName = taskEntity.name
                )
            )
        }
    }

    override suspend fun updateTaskState(taskEntity: TaskEntity) {
        retrofitErrorHandler.apiCall {
            eventApi.updateTaskStatus(
                CommonUpdateTaskStatusIn(
                    taskID = taskEntity.id.toInt(),
                    isDone = taskEntity.done
                )
            )
        }
    }

    override suspend fun deleteTask(listId: Long, taskId: Long) {
        retrofitErrorHandler.apiCall {
            eventApi.deleteTaskFromList(
                CommonDeleteTaskFromListIn(
                    listID = listId.toInt(),
                    taskID = taskId.toInt()
                )
            )
        }
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
    managersOnly = isVisible == "true",
    type = when (type) {
        "TODO" -> ListEntity.Type.TODO
        "BUY" -> ListEntity.Type.BUY
        "WISHLIST" -> ListEntity.Type.WISHLIST
        else -> ListEntity.Type.EMPTY
    }
)

private fun CommonTaskInfo.toDomain() = TaskEntity(
    id = taskID.toLong(),
    name = name,
    done = isDone,
    isServer = true
)
