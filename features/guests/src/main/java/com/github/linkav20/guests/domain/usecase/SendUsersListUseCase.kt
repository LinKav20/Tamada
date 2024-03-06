package com.github.linkav20.guests.domain.usecase

import com.github.linkav20.guests.domain.model.User
import com.github.linkav20.guests.domain.repository.UsersRepository
import javax.inject.Inject

class SendUsersListUseCase @Inject constructor(
    private val repository: UsersRepository,
) {

    suspend fun invoke(
        partyId: Long,
        users: List<User>
    ) = repository.sendUsers(partyId, users)
}
