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

package ru.ozon.ozon_pvz.network.my.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 
 *
 * @param partyID 
 * @param phoneNumber 
 */


data class CommonUpdatePartyWalletPhoneIn (

    @Json(name = "partyID")
    val partyID: kotlin.Int,

    @Json(name = "phoneNumber")
    val phoneNumber: kotlin.String

)

