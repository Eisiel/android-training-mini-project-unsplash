package com.example.miniproject.service

import android.content.Context
import android.content.SharedPreferences
import java.util.*


class SessionManager {
    val SESSION_PREFERENCE = "com.example.miniproject.SESSION_PREFERENCE"
    val SESSION_TOKEN = "com.example.miniproject.SESSION_TOKEN"
    val SESSION_EXPIRY_TIME = "com.example.miniproject.SESSION_EXPIRY_TIME"

    fun startUserSession(context: Context, expiredIn: Int) {
        val calendar: Calendar = Calendar.getInstance()
        val userLoggedTime: Date = calendar.getTime()
        calendar.setTime(userLoggedTime)
        calendar.add(Calendar.SECOND, expiredIn)
        val expiryTime: Date = calendar.getTime()
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(SESSION_EXPIRY_TIME, expiryTime.getTime())
        editor.apply()
    }
}