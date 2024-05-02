package com.github.linkav20.info.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.info.domain.model.Party
import com.github.linkav20.info.domain.repository.PartyRepository
import javax.inject.Inject

class SavePartyUseCase @Inject constructor(
    private val repository: PartyRepository,
    private val userInformationRepository: UserInformationRepository
) {
    suspend fun invoke(party: Party) {
        repository.saveParty(party = party, userId = userInformationRepository.userId)
    }
}
