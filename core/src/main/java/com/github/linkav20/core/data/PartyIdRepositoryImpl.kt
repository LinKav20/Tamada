package com.github.linkav20.core.data

import com.github.linkav20.core.domain.repository.PartyIdRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartyIdRepositoryImpl @Inject constructor(

) : PartyIdRepository {

    private var partyId: Long? = null
    private var partyName: String? = null

    override fun getPartyId(): Long? = partyId

    override fun setPartyId(id: Long) {
        partyId = id
    }

    override fun getPartyName(): String? = partyName

    override fun setPartyName(name: String) {
        partyName = name
    }
}