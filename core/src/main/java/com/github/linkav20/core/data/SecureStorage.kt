package com.github.linkav20.core.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.github.linkav20.core.di.Encrypted
import javax.inject.Inject

private const val DEFAULT_INT_VALUE = 0

class SecureStorage @Inject constructor(
    @Encrypted private val sharedPreferences: SharedPreferences
) {

    fun putString(key: String, value: String?) = sharedPreferences.edit { putString(key, value) }

    fun getString(key: String) = sharedPreferences.getString(key, null)

    fun putInt(key: String, value: Int?) =
        sharedPreferences.edit { putInt(key, value ?: DEFAULT_INT_VALUE) }

    fun getInt(key: String) = sharedPreferences.getInt(key, DEFAULT_INT_VALUE)

    fun putBoolean(key: String, value: Boolean) = sharedPreferences.edit { putBoolean(key, value) }
    fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)

    suspend fun clear() = sharedPreferences.edit { clear() }

    companion object {
        const val TOKEN_KEY = "TOKEN_KEY"
        const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
        const val USER_ID = "USER_ID"
        const val USER_AVATAR = "USER_AVATAR"
        const val USER_LOGIN = "USER_LOGIN"
        const val USER_IS_WALLED = "USER_IS_WALLED"
    }
}