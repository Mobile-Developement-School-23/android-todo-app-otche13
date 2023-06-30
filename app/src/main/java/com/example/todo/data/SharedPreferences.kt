package com.example.todo.data

import android.content.Context
import java.util.UUID

class SharedPreferencesApp(
    context: Context
) {
    private val sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    init {
        createId()
    }

    fun setCurrentToken(token: String?) {
        if (token == null)
            editor.remove(CURRENT_ACCOUNT_TOKEN)
        else
            editor.putString(CURRENT_ACCOUNT_TOKEN, token)
        editor.apply()
    }

    fun getCurrentToken(): String? = sharedPreferences.getString(CURRENT_ACCOUNT_TOKEN, null)

    private fun createId() {
        if (getId() == "0d") {
            editor.putString(DEVICE_TAG, UUID.randomUUID().toString())
            editor.apply()
        }
    }

    fun putRevisionId(revision: Int) {
        editor.putInt(REVISION_TAG, revision)
        editor.apply()
    }

    fun getRevisionId() : Int {
        return sharedPreferences.getInt(REVISION_TAG, 0)
    }

    fun getId() : String {
        return sharedPreferences.getString(DEVICE_TAG, "0d")?: "0d"
    }


    companion object {
        private const val CURRENT_ACCOUNT_TOKEN = "currentToken"
        private const val REVISION_TAG = "currentRevision"
        private const val DEVICE_TAG = "currentId"
    }

}