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
 * @param actionFrom 
 * @param coverID 
 * @param partyID 
 */


data class CommonUpdateEventCoverIDIn (

    @Json(name = "actionFrom")
    val actionFrom: kotlin.Int,

    @Json(name = "coverID")
    val coverID: kotlin.Int,

    @Json(name = "partyID")
    val partyID: kotlin.Int

)

