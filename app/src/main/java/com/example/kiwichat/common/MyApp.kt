package com.example.kiwichat.common

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp:Application (){

    override fun onCreate() {
        super.onCreate()
        appContext = this


    }

    companion object {
        lateinit var appContext: Application
    }



}