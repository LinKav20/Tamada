/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package com.github.linkav20.network.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param bank 
 * @param cardNumber 
 * @param cardOwner 
 * @param partyID 
 * @param phoneNumber 
 * @param walletID 
 */


data class CommonGetPartyWalletOut (

    @Json(name = "bank")
    val bank: kotlin.String? = null,

    @Json(name = "cardNumber")
    val cardNumber: kotlin.String? = null,

    @Json(name = "cardOwner")
    val cardOwner: kotlin.String? = null,

    @Json(name = "partyID")
    val partyID: kotlin.Int? = null,

    @Json(name = "phoneNumber")
    val phoneNumber: kotlin.String? = null,

    @Json(name = "walletID")
    val walletID: kotlin.Int? = null

)

