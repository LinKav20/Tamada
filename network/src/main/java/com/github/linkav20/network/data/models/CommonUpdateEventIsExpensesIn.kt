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
 * @param isExpenses 
 * @param partyID 
 */


data class CommonUpdateEventIsExpensesIn (

    @Json(name = "actionFrom")
    val actionFrom: kotlin.Int,

    @Json(name = "isExpenses")
    val isExpenses: kotlin.Int,

    @Json(name = "partyID")
    val partyID: kotlin.Int

)

