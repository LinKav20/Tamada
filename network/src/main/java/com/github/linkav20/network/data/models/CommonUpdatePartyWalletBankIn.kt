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
 * @param partyID 
 */


data class CommonUpdatePartyWalletBankIn (

    @Json(name = "bank")
    val bank: kotlin.String,

    @Json(name = "partyID")
    val partyID: kotlin.Int

)

