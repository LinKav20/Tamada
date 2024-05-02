package com.github.linkav20.home.data

import com.github.linkav20.home.domain.model.Party
import com.github.linkav20.home.domain.repository.PartyInfoRepository
import com.github.linkav20.network.data.api.EventApi
import com.github.linkav20.network.data.models.CommonGetUserEventsIn
import com.github.linkav20.network.data.models.CommonGetUserPartiesOut
import com.github.linkav20.network.utils.RetrofitErrorHandler
import javax.inject.Inject

class PartyInfoRepositoryImpl @Inject constructor(
    private val eventApi: EventApi,
    private val retrofitErrorHandler: RetrofitErrorHandler
) : PartyInfoRepository {
    override suspend fun getAllParties(userId: Int) = retrofitErrorHandler.apiCall {
        eventApi.getUserEvents(CommonGetUserEventsIn(userId))
    }?.map { it.toDomain() } ?: emptyList()
}

private fun CommonGetUserPartiesOut.toDomain() = Party(
    id = partyID.toLong(),
    name = name,
    isNew = isNew,
    isManager = isManager,
    cover = coverID
)
