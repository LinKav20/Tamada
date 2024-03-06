package com.github.linkav20.info.data

import com.github.linkav20.core.utils.DateTimeUtils
import com.github.linkav20.info.domain.model.Party
import com.github.linkav20.info.domain.repository.PartyRepository
import timber.log.Timber
import java.time.OffsetDateTime
import javax.inject.Inject

class PartyRepositoryImpl @Inject constructor(
    // private val retrofitErrorHandler: RetrofitErrorHandler,
) : PartyRepository {
    override suspend fun saveParty(party: Party) {
        Timber.e("Party saved")
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
