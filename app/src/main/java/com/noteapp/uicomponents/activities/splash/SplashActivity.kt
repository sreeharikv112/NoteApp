package com.noteapp.uicomponents.activities.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.noteapp.R
import com.noteapp.R.id.*
import com.noteapp.uicomponents.activities.landing.MainActivity


class SplashActivity : Activity() {

    private val mHideHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        mHideHandler.postDelayed(Runnable { delayedAction() },AUTO_HIDE_DELAY_MILLIS)
    }

    private fun delayedAction(){
        var intent= Intent(this@SplashActivity , MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
       private val AUTO_HIDE_DELAY_MILLIS = 3000L
    }
}
