package com.github.linkav20.guests.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.guests.domain.repository.UsersRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UsersRepository,
    private val userInformationRepository: UserInformationRepository
) {

    suspend fun invoke(partyId: Long, userId: Int) {
        val user = userInformationRepository.userId
        repository.deleteUser(
            userId = userId,
            actionFrom = user,
            partyId = partyId
        )
    }
}