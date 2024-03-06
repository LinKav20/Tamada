package com.github.linkav20.home.data

import com.github.linkav20.home.domain.model.Party
import com.github.linkav20.home.domain.repository.PartyInfoRepository
import javax.inject.Inject

class PartyInfoRepositoryImpl @Inject constructor(

) : PartyInfoRepository {
    override suspend fun getAllParties(): List<Party> =
        listOf(
            Party(1L, "Dima's b-day", 0, false, true),
            Party(2L, "Event1", 1, true, false),
        )
}
