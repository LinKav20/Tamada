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
 * @param actionFrom 
 * @param name 
 * @param partyID 
 */


data class CommonUpdateEventNameIn (

    @Json(name = "actionFrom")
    val actionFrom: kotlin.Int,

    @Json(name = "name")
    val name: kotlin.String,

    @Json(name = "partyID")
    val partyID: kotlin.Int

)

