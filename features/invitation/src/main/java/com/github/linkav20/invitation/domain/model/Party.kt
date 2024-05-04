package com.github.linkav20.invitation.domain.model

import java.time.OffsetDateTime

data class Party(
    val id: Int,
    val name: String,
    val startTime: OffsetDateTime?,
    val address: String?,
    val cover: Int
)
