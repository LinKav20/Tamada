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
 * @param avatarID 
 * @param login 
 * @param role 
 * @param status 
 * @param userID 
 */


data class CommonGetPartiesGuestsOut (

    @Json(name = "avatarID")
    val avatarID: kotlin.Int? = null,

    @Json(name = "login")
    val login: kotlin.String? = null,

    @Json(name = "role")
    val role: kotlin.String? = null,

    @Json(name = "status")
    val status: kotlin.String? = null,

    @Json(name = "userID")
    val userID: kotlin.Int? = null

)

