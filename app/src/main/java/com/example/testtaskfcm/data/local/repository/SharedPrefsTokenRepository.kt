package com.example.testtaskfcm.data.local.repository

import android.content.Context
import com.example.testtaskfcm.domain.repository.TokenRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefsTokenRepository @Inject constructor(
    @ApplicationContext context: Context
) : TokenRepository {
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    override fun saveToken(token: String) {
        sharedPreferences.edit().putString("fcm_token", token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString("fcm_token", null)
    }
}