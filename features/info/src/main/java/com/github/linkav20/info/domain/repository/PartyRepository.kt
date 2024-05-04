package com.github.linkav20.info.domain.repository

import com.github.linkav20.info.domain.model.Party
import java.time.OffsetDateTime

interface PartyRepository {
    suspend fun saveParty(party: Party, userId: Int)

    suspend fun getParty(id: Long): Party?

    suspend fun updateName(name: String, partyId: Int, userId: Int)

    suspend fun updateStartTime(time: OffsetDateTime, partyId: Int, userId: Int)

    suspend fun updateEndTime(time: OffsetDateTime, partyId: Int, userId: Int)

    suspend fun updateAddress(address: String, partyId: Int, userId: Int)

    suspend fun updateAddressAdditional(address: String, partyId: Int, userId: Int)

    suspend fun updateInformation(info: String, partyId: Int, userId: Int)

    suspend fun updateTheme(theme: String, partyId: Int, userId: Int)

    suspend fun updateDresscode(dresscode: String, partyId: Int, userId: Int)

    suspend fun updateMoodboardLink(link: String, partyId: Int, userId: Int)

    suspend fun updateAddressLink(link: String, partyId: Int, userId: Int)
}
