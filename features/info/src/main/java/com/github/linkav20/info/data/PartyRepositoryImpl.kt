package com.github.linkav20.info.data

import com.github.linkav20.core.utils.DateTimeUtils
import com.github.linkav20.info.domain.model.Party
import com.github.linkav20.info.domain.repository.PartyRepository
import com.github.linkav20.network.data.api.EventApi
import com.github.linkav20.network.data.models.CommonCreateEventIn
import com.github.linkav20.network.data.models.CommonGetEventIn
import com.github.linkav20.network.data.models.CommonGetEventOut
import com.github.linkav20.network.data.models.CommonUpdateEventAddressAdditionalInfoIn
import com.github.linkav20.network.data.models.CommonUpdateEventAddressIn
import com.github.linkav20.network.data.models.CommonUpdateEventAddressLinkIn
import com.github.linkav20.network.data.models.CommonUpdateEventDressCodeIn
import com.github.linkav20.network.data.models.CommonUpdateEventEndTimeIn
import com.github.linkav20.network.data.models.CommonUpdateEventImportantIn
import com.github.linkav20.network.data.models.CommonUpdateEventMoodboardLinkIn
import com.github.linkav20.network.data.models.CommonUpdateEventStartTimeIn
import com.github.linkav20.network.data.models.CommonUpdateEventThemeIn
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

    override suspend fun getParty(id: Long): Party? = retrofitErrorHandler.apiCall {
        eventApi.getEvent(CommonGetEventIn(id.toInt()))
    }?.toDomain()

    override suspend fun updateName(name: String, partyId: Int, userId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateStartTime(time: OffsetDateTime, partyId: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.updateStartTime(
                CommonUpdateEventStartTimeIn(
                    partyID = partyId,
                    actionFrom = userId,
                    newStartTime = DateTimeUtils.toString(time)
                )
            )
        }
    }

    override suspend fun updateEndTime(time: OffsetDateTime, partyId: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.updateEndTime(
                CommonUpdateEventEndTimeIn(
                    partyID = partyId,
                    actionFrom = userId,
                    newEndTime = DateTimeUtils.toString(time)
                )
            )
        }
    }

    override suspend fun updateAddress(address: String, partyId: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.updateAddress(
                CommonUpdateEventAddressIn(
                    partyID = partyId,
                    actionFrom = userId,
                    address = address
                )
            )
        }
    }

    override suspend fun updateAddressAdditional(address: String, partyId: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.updateAddressAdditionalInfo(
                CommonUpdateEventAddressAdditionalInfoIn(
                    partyID = partyId,
                    actionFrom = userId,
                    addressAdditionalInfo = address
                )
            )
        }
    }

    override suspend fun updateInformation(info: String, partyId: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.updateImportant(
                CommonUpdateEventImportantIn(
                    partyID = partyId,
                    actionFrom = userId,
                    important = info
                )
            )
        }
    }

    override suspend fun updateTheme(theme: String, partyId: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.updateTheme(
                CommonUpdateEventThemeIn(
                    partyID = partyId,
                    actionFrom = userId,
                    theme = theme
                )
            )
        }
    }

    override suspend fun updateDresscode(dresscode: String, partyId: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.updateDressCode(
                CommonUpdateEventDressCodeIn(
                    partyID = partyId,
                    actionFrom = userId,
                    dressCode = dresscode
                )
            )
        }
    }

    override suspend fun updateMoodboardLink(link: String, partyId: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.updateMoodboard(
                CommonUpdateEventMoodboardLinkIn(
                    partyID = partyId,
                    actionFrom = userId,
                    moodboardLink = link
                )
            )
        }
    }

    override suspend fun updateAddressLink(link: String, partyId: Int, userId: Int) {
        retrofitErrorHandler.apiCall {
            eventApi.updateAddressLink(
                CommonUpdateEventAddressLinkIn(
                    partyID = partyId,
                    actionFrom = userId,
                    addressLink = link
                )
            )
        }
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
    addressLink = addressLink,
    startTime = startTime?.let { DateTimeUtils.toString(it) },
    theme = theme
)

private fun CommonGetEventOut.toDomain() = Party(
    id = partyID,
    name = name,
    startTime = startTime?.let { DateTimeUtils.fromString(it) },
    endTime = endTime?.let { DateTimeUtils.fromString(it) },
    address = address,
    addressLink = addressLink,
    addressAdditional = addressAdditional,
    isExpenses = isExpenses != null,
    important = important,
    theme = theme,
    dressCode = dressCode,
    moodboadLink = moodboadLink,
)
