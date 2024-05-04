package com.github.linkav20.invitation.data

import com.github.linkav20.core.utils.DateTimeUtils
import com.github.linkav20.invitation.domain.model.Party
import com.github.linkav20.invitation.domain.repository.PartyInvitationRepository
import com.github.linkav20.network.data.api.EventApi
import com.github.linkav20.network.data.models.CommonAddUserToPartyIn
import com.github.linkav20.network.data.models.CommonGetEventIn
import com.github.linkav20.network.data.models.CommonGetEventOut
import com.github.linkav20.network.utils.RetrofitErrorHandler
import javax.inject.Inject

class PartyInvitationRepositoryImpl @Inject constructor(
    private val retrofitErrorHandler: RetrofitErrorHandler,
    private val eventApi: EventApi
) : PartyInvitationRepository {
    override suspend fun getPartyInfo(id: Int): Party? = retrofitErrorHandler.apiCall {
        eventApi.getEvent(CommonGetEventIn(id))
    }?.toDomain()

    override suspend fun addUserToParty(partyId: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.addUserToEvent(
                CommonAddUserToPartyIn(
                    partyID = partyId,
                    userID = userId
                )
            )
        }
    }
}

private fun CommonGetEventOut.toDomain() = Party(
    id = partyID,
    name = name,
    address = address,
    startTime = startTime?.let { DateTimeUtils.fromString(it) },
    cover = coverID
)