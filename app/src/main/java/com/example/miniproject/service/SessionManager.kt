package com.example.miniproject.service

import android.app.Application
import android.util.Log
import com.example.miniproject.ui.home.UnsplashSearchFragment
import java.util.*


class SessionManager : Application() {

    private val logoutListener: LogoutListener? = null
    private val timer: Timer? = null

    interface LogoutListener {
        fun doLogout()
    }
    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        lateinit var binding: UnsplashSearchFragment
        private var logoutListener: LogoutListener? = null
        private var timer: Timer? = null
        fun userSessionStart() {
            if (timer != null) {
                timer!!.cancel()
            }
            timer = Timer()
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    if (logoutListener != null) {
                        Log.d("Login","Session end")
                        logoutListener!!.doLogout()
                    }
                }
            }, 30000)
        }

        fun resetSession() {
            userSessionStart()
        }

        fun registerSessionListener(listener: UnsplashSearchFragment) {
            logoutListener = listener
        }
    }
}
