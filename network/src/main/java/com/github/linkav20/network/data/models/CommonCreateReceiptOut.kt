package com.github.linkav20.network.data.models

import com.squareup.moshi.Json

data class CommonCreateReceiptOut(
    @Json(name = "expensesID")
    val expensesID: kotlin.Long,
)
