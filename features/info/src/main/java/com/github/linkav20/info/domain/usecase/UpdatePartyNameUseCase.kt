package com.github.linkav20.info.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.info.domain.repository.PartyRepository
import javax.inject.Inject

class UpdatePartyNameUseCase @Inject constructor(
    private val repository: PartyRepository,
    private val userInformationRepository: UserInformationRepository
) {

    suspend fun invoke(partyId: Int, name: String) {
        val userId = userInformationRepository.userId
        repository.updateName(
            name = name,
            userId = userId,
            partyId = partyId
        )
    }
}