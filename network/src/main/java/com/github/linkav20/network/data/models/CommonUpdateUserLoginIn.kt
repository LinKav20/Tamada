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
 * @param newLogin 
 * @param userID 
 */


data class CommonUpdateUserLoginIn (

    @Json(name = "newLogin")
    val newLogin: kotlin.String,

    @Json(name = "userID")
    val userID: kotlin.Int

)

