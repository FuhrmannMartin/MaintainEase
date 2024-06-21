package com.example.maintainease.data.entities

import android.content.Context
import android.content.SharedPreferences

object UserPreferences {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_STAFF_ID = "staff_id"
    private const val KEY_TEAM_ID = "team_id"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveCurrentUser(context: Context, staffId: Int, teamId: Int) {
        val editor = getPreferences(context).edit()
        editor.putInt(KEY_STAFF_ID, staffId)
        editor.putInt(KEY_TEAM_ID, teamId)
        editor.apply()
    }

    fun getCurrentUser(context: Context): Map<String, Int> {
        val prefs = getPreferences(context)
        val staffId = prefs.getInt(KEY_STAFF_ID, -1)
        val teamId = prefs.getInt(KEY_TEAM_ID, -1)
        return if (staffId != -1 && teamId != -1) {
            mapOf("staffId" to staffId, "teamId" to teamId)
        } else {
            emptyMap()
        }
    }
}