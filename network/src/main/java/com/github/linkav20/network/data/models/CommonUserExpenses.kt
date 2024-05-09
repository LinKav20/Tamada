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
 * @param expensesID 
 * @param name 
 * @param partyID 
 * @param sum 
 * @param type 
 * @param userID 
 */


data class CommonUserExpenses (

    @Json(name = "expensesID")
    val expensesID: kotlin.Int,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "partyID")
    val partyID: kotlin.Int? = null,

    @Json(name = "sum")
    val sum: kotlin.Long? = null,

    @Json(name = "type")
    val type: kotlin.String? = null,

    @Json(name = "userID")
    val userID: kotlin.Int? = null

)

