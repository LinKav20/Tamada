package com.github.linkav20.info.domain.usecase

import com.github.linkav20.core.domain.repository.UserInformationRepository
import com.github.linkav20.core.domain.usecase.GetPartyIdUseCase
import com.github.linkav20.info.domain.repository.PartyRepository
import java.time.OffsetDateTime
import javax.inject.Inject

class UpdatePartyStartTimeUseCase @Inject constructor(
    private val partyRepository: PartyRepository,
    private val userInformationRepository: UserInformationRepository,
    private val getPartyIdUseCase: GetPartyIdUseCase
) {

    suspend fun invoke(startTime: OffsetDateTime) {
        val userId = userInformationRepository.userId
        val partyId = getPartyIdUseCase.invoke() ?: return
        partyRepository.updateStartTime(
            time = startTime,
            userId = userId,
            partyId = partyId.toInt()
        )
    }
}
