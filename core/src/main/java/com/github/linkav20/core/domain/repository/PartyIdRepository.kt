package com.github.linkav20.core.domain.repository

interface PartyIdRepository {

    fun getPartyId(): Long?

    fun setPartyId(id: Long)
}