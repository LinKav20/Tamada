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
 * @param newAvatarID 
 * @param userID 
 */


data class CommonUpdateUserAvatarIn (

    @Json(name = "newAvatarID")
    val newAvatarID: kotlin.Int,

    @Json(name = "userID")
    val userID: kotlin.Int

)

