package com.example.githubproject

import android.app.Application
import com.example.githubproject.util.startNetworkCallback
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startNetworkCallback() //measure when the wifi drops off and when it returns
    }
}