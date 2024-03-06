package com.github.linkav20.network.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val returnCode: String? = null,
)
