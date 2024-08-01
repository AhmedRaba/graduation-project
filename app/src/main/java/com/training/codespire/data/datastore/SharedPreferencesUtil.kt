package com.training.codespire.data.datastore

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {

    companion object {
        private const val PREFS_FILENAME = "MyPrefs"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_TOKEN = "token"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_EMAIL_CODE = "emailCode"
        private const val KEY_CATEGORY_ID = "categoryId"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var token: String?
        get() = sharedPreferences.getString(KEY_TOKEN, null)
        set(value) = sharedPreferences.edit().putString(KEY_TOKEN, value).apply()

    var username: String?
        get() = sharedPreferences.getString(KEY_USERNAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_USERNAME, value).apply()
    var email: String?
        get() = sharedPreferences.getString(KEY_EMAIL, null)
        set(value) = sharedPreferences.edit().putString(KEY_EMAIL, value).apply()
    var emailCode: String?
        get() = sharedPreferences.getString(KEY_EMAIL_CODE, null)
        set(value) = sharedPreferences.edit().putString(KEY_EMAIL_CODE, value).apply()

    var categoryId: Int
        get() = sharedPreferences.getInt(KEY_CATEGORY_ID, 0)
        set(value) = sharedPreferences.edit().putInt(KEY_CATEGORY_ID, value).apply()

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
