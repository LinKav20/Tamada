package com.github.linkav20.info.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.info.domain.repository.PartyRepository
import javax.inject.Inject

class UpdatePartyAddressAdditionalUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    private val userInformationRepository: UserInformationRepository
) {

    suspend fun invoke(partyId: Int, address: String) {
        val userId = userInformationRepository.userId
        partyRepository.updateAddressAdditional(
            address = address,
            userId = userId,
            partyId = partyId
        )
    }
}
