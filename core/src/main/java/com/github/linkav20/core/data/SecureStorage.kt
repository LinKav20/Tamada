package com.github.linkav20.core.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.github.linkav20.core.di.Encrypted
import javax.inject.Inject

class SecureStorage @Inject constructor(
    @Encrypted private val sharedPreferences: SharedPreferences
) {

    fun putString(key: String, value: String?) = sharedPreferences.edit { putString(key, value) }

    fun getString(key: String) = sharedPreferences.getString(key, null)

    suspend fun clear() = sharedPreferences.edit { clear() }

    companion object {
        const val TOKEN_KEY = "TOKEN_KEY"
        const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY"
    }
}