package com.github.linkav20.invitation.domain.usecase

import com.github.linkav20.invitation.domain.repository.PartyInvitationRepository
import javax.inject.Inject

class GetPartyInfoUseCase @Inject constructor(
    private val repository: PartyInvitationRepository
) {
    suspend fun invoke(id: Int) = repository.getPartyInfo(id)
}
