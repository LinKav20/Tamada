package com.github.linkav20.info.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.info.domain.repository.PartyRepository
import javax.inject.Inject

class UpdatePartyAddressUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    private val userInformationRepository: UserInformationRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {

    suspend fun invoke(address: String) {
        val userId = userInformationRepository.userId
        val partyId = getPartyIdUseCase.invoke() ?: return
        partyRepository.updateAddress(
            address = address,
            userId = userId,
            partyId = partyId.toInt()
        )
    }
}
