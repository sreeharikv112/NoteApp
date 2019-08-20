package com.noteapp.db

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper(var context: Context) {

    val mSharedPreference : SharedPreferences
    val mSharedEditor : SharedPreferences.Editor
    val SHARED_PREF = "SHARED_PREF"

    init {
        mSharedPreference = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        mSharedEditor = mSharedPreference.edit()
    }

    fun saveBoolData(key:String , value:Boolean){
        mSharedEditor.putBoolean(key,value)
        mSharedEditor.commit()
    }

    fun getBoolData(key:String ): Boolean{
        return mSharedPreference.getBoolean(key,false)
    }

}