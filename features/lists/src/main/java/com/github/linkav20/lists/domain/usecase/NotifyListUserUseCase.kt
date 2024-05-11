package com.github.linkav20.lists.domain.usecase

import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.lists.domain.repository.ListsRepository
import com.github.linkav20.notifications.domain.usecase.NotifyUseCase
import javax.inject.Inject

class NotifyListUserUseCase @Inject constructor(
    private val repository: ListsRepository,
    private val notifyUseCase: NotifyUseCase,
    private val getPartyIdUseCase: GetPartyIdUseCase,
) {

    suspend fun invoke(
        forManagers: Boolean,
        title: String,
        subtitle: String
    ) {
        val partyId = getPartyIdUseCase.invoke() ?: return
        val users = repository.getUsers(partyId)
        users.filter { if (forManagers) it.isManager else !it.isManager }.forEach {
            notifyUseCase.invoke(
                toUserId = it.id,
                title = title,
                subtitle = subtitle
            )
        }
    }
}
