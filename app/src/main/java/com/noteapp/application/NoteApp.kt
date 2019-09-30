package com.noteapp.application

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import leakcanary.AppWatcher
import leakcanary.LeakCanary

class NoteApp(): Application() {

    var isAppActive:Boolean = false

    override fun onCreate() {
        super.onCreate()
        //Initiate listening to minimal of app start and stop events
        ProcessLifecycleOwner.get().lifecycle.addObserver(NoteAppEventListener(this))

        AppWatcher.config = AppWatcher.config.copy(watchFragmentViews = false)
        
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }
}