package com.noteapp.common

import android.util.Log

class AppLogger {

    fun debug(tag:String, log:String){
        Log.d(tag,log);
    }

    fun verbose(tag:String, log:String){
        Log.v(tag,log);
    }

    fun info(tag:String, log:String){
        Log.i(tag,log);
    }

    fun error(tag:String, log:String){
        Log.e(tag,log);
    }

}