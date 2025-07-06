package com.pilabor.resonance.data

import android.content.Context
import org.koin.core.annotation.Single
import androidx.core.content.edit
import com.pilabor.resonance.mediaSource.api.MediaSourceInterface
import com.pilabor.resonance.mediaSource.implementation.mock.MockMediaSource

@Single
class MusifySession(private val context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("musify_session", Context.MODE_PRIVATE)








    fun saveToken(token: String) {
        sharedPreferences.edit {
            putString("token", token)
        }
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun saveUserName(userName: String) {
        sharedPreferences.edit {
            putString("user_name", userName)
        }
    }

    fun getUserName(): String? {
        return sharedPreferences.getString("user_name", null)
    }

    fun clearSession() {
        sharedPreferences.edit() {
            clear()
        }
    }

    fun getActiveMediaSource(): MediaSourceInterface {
        return MockMediaSource("mock", "Mock Media Source");
    }
}