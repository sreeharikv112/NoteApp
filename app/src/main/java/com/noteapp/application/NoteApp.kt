package com.noteapp.application

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner

class NoteApp(): Application() {

    var isAppActive:Boolean = false

    override fun onCreate() {
        super.onCreate()
        //Initiate listening to minimal of app start and stop events
        ProcessLifecycleOwner.get().lifecycle.addObserver(NoteAppEventListener(this))
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}