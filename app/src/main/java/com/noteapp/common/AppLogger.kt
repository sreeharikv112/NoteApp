package com.noteapp.common

import android.util.Log

class AppLogger {

    fun d(tag:String, log:String){
        Log.d(tag,log);
    }

    fun v(tag:String, log:String){
        Log.v(tag,log);
    }

    fun i(tag:String, log:String){
        Log.i(tag,log);
    }

    fun e(tag:String, log:String){
        Log.e(tag,log);
    }

}