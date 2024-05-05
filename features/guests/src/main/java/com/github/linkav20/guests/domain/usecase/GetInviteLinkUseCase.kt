package com.github.linkav20.guests.domain.usecase

import com.github.linkav20.guests.domain.repository.UsersRepository
import javax.inject.Inject

class GetInviteLinkUseCase @Inject constructor(
    private val repository: UsersRepository
) {

    suspend fun invoke(partyId: Long) = repository.getInviteLink(partyId)
}
