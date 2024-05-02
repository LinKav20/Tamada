package com.github.linkav20.info.data

import com.github.linkav20.core.utils.DateTimeUtils
import com.github.linkav20.info.domain.model.Party
import com.github.linkav20.info.domain.repository.PartyRepository
import com.github.linkav20.network.data.api.EventApi
import com.github.linkav20.network.data.models.CommonCreateEventIn
import com.github.linkav20.network.utils.RetrofitErrorHandler
import timber.log.Timber
import java.time.OffsetDateTime
import javax.inject.Inject

class PartyRepositoryImpl @Inject constructor(
    private val retrofitErrorHandler: RetrofitErrorHandler,
    private val eventApi: EventApi
) : PartyRepository {
    override suspend fun saveParty(party: Party, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.createEvent(party.toRequest().copy(userCreatorID = userId))
        }
    }

    override suspend fun getParty(id: Long): Party? {
        return Party(
            name = "Party name",
            startTime = OffsetDateTime.now(),
            endTime = OffsetDateTime.now().plusMinutes(2),
            address = "Black Sea",
            addressAdditional = "Go go go go",
            addressLink = "https://some-link-okaaaay",
            isExpenses = true,
            important = null,
            theme = null,
            dressCode = null,
            moodboadLink = null,
        )
    }
}

private fun Party.toRequest() = CommonCreateEventIn(
    userCreatorID = 0,
    address = address,
    addressAdditional = addressAdditional,
    dressCode = dressCode,
    endTime = endTime?.let { DateTimeUtils.toString(it) },
    important = important,
    isExpenses = if (isExpenses) 1 else 0,
    moodboadLink = moodboadLink,
    name = name,
    startTime = startTime?.let { DateTimeUtils.toString(it) },
    theme = theme
)
