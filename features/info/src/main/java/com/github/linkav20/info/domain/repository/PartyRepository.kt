package com.github.linkav20.info.domain.repository

import com.github.linkav20.info.domain.model.Party

interface PartyRepository {
    suspend fun saveParty(party: Party)

    suspend fun getParty(id: Long):  Party?
}
