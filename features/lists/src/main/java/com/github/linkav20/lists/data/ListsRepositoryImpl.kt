package com.github.linkav20.lists.data

import android.util.Log
import com.github.linkav20.lists.domain.entity.ListEntity
import com.github.linkav20.lists.domain.entity.TaskEntity
import com.github.linkav20.lists.domain.repository.ListsRepository
import javax.inject.Inject

class ListsRepositoryImpl @Inject constructor() : ListsRepository {
    override suspend fun getLists(partyId: Long): List<ListEntity> {
        return listOf(
            ListEntity(
                id = 1,
                type = ListEntity.Type.WISHLIST,
                tasks = listOf(
                    TaskEntity(1, "Hello8"),
                    TaskEntity(2, "Hello7", true),
                    TaskEntity(3, "Hello6"),
                    TaskEntity(4, "Hello5"),
                    TaskEntity(5, "Hello4", true),
                    TaskEntity(6, "Hello3", true),
                    TaskEntity(7, "Hello2"),
                    TaskEntity(8, "Hello9"),
                    TaskEntity(9, "Hello1"),
                    TaskEntity(10, "Hello4", true),
                    TaskEntity(11, "Hello3", true),
                    TaskEntity(12, "Hello2"),
                    TaskEntity(13, "Hello9"),
                    TaskEntity(14, "Hello1"),
                    TaskEntity(15, "Hello4", true),
                    TaskEntity(16, "Hello3", true),
                    TaskEntity(17, "Hello2"),
                    TaskEntity(18, "Hello9"),
                    TaskEntity(19, "Hello1"),
                )
            )
        )
    }

    override suspend fun getListById(listId: Long): ListEntity {
        return ListEntity(
            id = 1,
            type = ListEntity.Type.WISHLIST,
            tasks = listOf(
                TaskEntity(1, "Hello8"),
                TaskEntity(2, "Hello7", true),
                TaskEntity(3, "Hello6"),
                TaskEntity(4, "Hello5"),
                TaskEntity(5, "Hello4", true),
                TaskEntity(6, "Hello3", true),
                TaskEntity(7, "Hello2"),
                TaskEntity(8, "Hello9"),
                TaskEntity(9, "Hello1"),
                TaskEntity(10, "Hello4", true),
                TaskEntity(11, "Hello3", true),
                TaskEntity(12, "Hello2"),
                TaskEntity(13, "Hello9"),
                TaskEntity(14, "Hello1"),
                TaskEntity(15, "Hello4", true),
                TaskEntity(16, "Hello3", true),
                TaskEntity(17, "Hello2"),
                TaskEntity(18, "Hello9"),
                TaskEntity(19, "Hello1"),
            )
        )
    }

    override suspend fun sendList(list: ListEntity) {
        Log.d("MY_", "Sended $list")
    }
}