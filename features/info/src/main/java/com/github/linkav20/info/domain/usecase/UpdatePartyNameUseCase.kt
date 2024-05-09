package com.github.linkav20.info.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.info.domain.repository.PartyRepository
import javax.inject.Inject

class UpdatePartyNameUseCase @Inject constructor(
    private val repository: PartyRepository,
    private val userInformationRepository: UserInformationRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {

    suspend fun invoke(name: String) {
        val userId = userInformationRepository.userId
        val partyId = getPartyIdUseCase.invoke() ?: return
        repository.updateName(
            name = name,
            userId = userId,
            partyId = partyId.toInt()
        )
    }
}