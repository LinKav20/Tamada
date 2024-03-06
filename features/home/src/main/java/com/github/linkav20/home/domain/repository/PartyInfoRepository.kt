package com.github.linkav20.home.domain.repository

import com.github.linkav20.home.domain.model.Party

interface PartyInfoRepository {
    suspend fun getAllParties(): List<Party>
}
