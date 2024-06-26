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
 * @param newRole available confirmed, uncertain, unconfirmed
 * @param partyID 
 * @param userID 
 */


data class CommonUpdateUserRoleOnPartyIn (

    @Json(name = "actionFrom")
    val actionFrom: kotlin.Int,

    /* available confirmed, uncertain, unconfirmed */
    @Json(name = "newRole")
    val newRole: kotlin.String,

    @Json(name = "partyID")
    val partyID: kotlin.Int,

    @Json(name = "userID")
    val userID: kotlin.Int

)

