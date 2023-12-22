package com.capstone.setravelapp.data.local.session

import android.content.Context
import android.content.SharedPreferences
import com.capstone.setravelapp.data.remote.response.LoginResponse

class StorySession(context: Context) {
    private var preferences: SharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun setUser(value: LoginResponse) {
        val edit = preferences.edit()
        edit.putString(ACCESS_TOKEN, value.accessToken)
        edit.apply()
    }

    fun isLogin(): Boolean {
        return preferences.contains(ACCESS_TOKEN)
    }

    fun getUser(): LoginResponse {
        return LoginResponse(
            accessToken = preferences.getString(ACCESS_TOKEN, "")
        )
    }

    companion object {
        private const val ACCESS_TOKEN = "accessToken"
        private const val PREFS = "prefs"
    }
}
