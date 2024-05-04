package com.github.linkav20.info.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.info.domain.repository.PartyRepository
import java.time.OffsetDateTime
import javax.inject.Inject

class UpdatePartyStartTimeUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    private val userInformationRepository: UserInformationRepository
) {

    suspend fun invoke(partyId: Int, startTime: OffsetDateTime) {
        val userId = userInformationRepository.userId
        partyRepository.updateStartTime(
            time = startTime,
            userId = userId,
            partyId = partyId
        )
    }
}
