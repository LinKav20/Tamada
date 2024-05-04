package com.github.linkav20.info.domain.model

import java.time.OffsetDateTime

data class Party(
    val id: Int = 0,
    val name: String,
    val startTime: OffsetDateTime? = null,
    val endTime: OffsetDateTime? = null,
    val address: String? = null,
    val addressLink: String? = null,
    val addressAdditional: String? = null,
    val isExpenses: Boolean = true,
    val important: String? = null,
    val theme: String? = null,
    val dressCode: String? = null,
    val moodboadLink: String? = null,
)
