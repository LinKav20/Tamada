package com.github.linkav20.invitation.domain.repository

import com.github.linkav20.invitation.domain.model.Party

interface PartyInvitationRepository {

    suspend fun getPartyInfo(id: Int): Party?

    suspend fun addUserToParty(partyId: Int, userId: Int)
}
