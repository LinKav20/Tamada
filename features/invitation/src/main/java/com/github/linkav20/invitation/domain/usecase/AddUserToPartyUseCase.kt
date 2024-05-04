package com.github.linkav20.invitation.domain.usecase

import com.github.linkav20.core.data.SecureStorage
import com.github.linkav20.core.domain.entity.DomainException
import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.invitation.domain.repository.PartyInvitationRepository
import javax.inject.Inject

class AddUserToPartyUseCase @Inject constructor(
    private val repository: PartyInvitationRepository,
    private val getUserInformationRepository: UserInformationRepository
) {

    suspend fun invoke(partyId: Int) {
        val userId = getUserInformationRepository.userId
        if (userId == SecureStorage.DEFAULT_INT_VALUE) throw DomainException.Unauthorized()
        repository.addUserToParty(
            partyId = partyId,
            userId = userId
        )
    }
}