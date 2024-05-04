package com.github.linkav20.guests.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.guests.domain.model.User
import com.github.linkav20.guests.domain.repository.UsersRepository
import javax.inject.Inject

class UpdateUserRoleUseCase @Inject constructor(
    private val repository: UsersRepository,
    private val userInformationRepository: UserInformationRepository
) {

    suspend fun invoke(
        partyId: Long,
        user: User
    ) {
        val userId = userInformationRepository.userId
        repository.updateUserRole(
            partyId = partyId,
            actionFrom = userId,
            userId = user.id,
            role = user.role
        )
    }
}
