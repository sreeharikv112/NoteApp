package com.noteapp.application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class NoteAppEventListener(val app: NoteApp): LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun appStarted(){
        app.isAppActive=true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun appStopped(){
        app.isAppActive=false
    }

}