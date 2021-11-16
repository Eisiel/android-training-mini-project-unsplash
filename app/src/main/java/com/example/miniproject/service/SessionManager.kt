package com.example.miniproject.service

import android.app.Application
import android.util.Log
import com.example.miniproject.ui.home.PhotoDetailFragment
import com.example.miniproject.ui.home.UnsplashSearchFragment
import com.example.miniproject.ui.logout.LogoutFragment
import java.util.*


class SessionManager : Application() {
    interface LogoutListener {
        fun doLogout()
    }

    companion object {
        private var logoutListener: LogoutListener? = null
        private var timer: Timer? = null
        private fun userSessionStart() {
            if (timer != null) {
                timer!!.cancel()
            }
            timer = Timer()
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    if (logoutListener != null) {
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

        fun registerSessionListener(listener: PhotoDetailFragment) {
            logoutListener = listener
        }

        fun registerSessionListener(listener: LogoutFragment) {
            logoutListener = listener
        }
    }
}
