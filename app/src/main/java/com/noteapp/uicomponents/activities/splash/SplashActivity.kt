package com.noteapp.uicomponents.activities.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.noteapp.R
import com.noteapp.R.id.*
import com.noteapp.common.Constants
import com.noteapp.db.SharedPreferenceHelper
import com.noteapp.models.SecurityQuestionModel
import com.noteapp.models.SecurityQuestionViewModel
import com.noteapp.uicomponents.activities.enterpin.PinActivity
import com.noteapp.uicomponents.activities.landing.MainActivity
import com.noteapp.uicomponents.activities.settings.SettingsActivity
import com.noteapp.uicomponents.activities.setuppin.IGetSecurityQuestionListener


class SplashActivity : Activity(), IGetSecurityQuestionListener {

    private val mHideHandler = Handler()
    lateinit var mSharedPrefHelper : SharedPreferenceHelper
    var mKeyRequiredStatus = false
    lateinit var intentObj: Intent
    lateinit var securityQstnVM : SecurityQuestionViewModel
    lateinit var mSecurityFetched : SecurityQuestionModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        securityQstnVM = SecurityQuestionViewModel(application)
        securityQstnVM.getSecurityQuestion(this, SettingsActivity.OPERATION.BUTTON_CLICKED)
        mHideHandler.postDelayed({ delayedAction() },AUTO_HIDE_DELAY_MILLIS)
    }

    private fun delayedAction(){

        mSharedPrefHelper = SharedPreferenceHelper(this)
        mKeyRequiredStatus = mSharedPrefHelper.getBoolData(Constants.KEY_PIN_REQUIRED)

        if(mKeyRequiredStatus){
            intentObj= Intent(this@SplashActivity , PinActivity::class.java)
            intentObj.putExtra(Constants.NAVIGATE_TO_HOME,true)
            intentObj.putExtra(Constants.LAST_PIN,mSecurityFetched.key)
            intentObj.putExtra(Constants.QUESTION,mSecurityFetched.question)
            intentObj.putExtra(Constants.ANSWER,mSecurityFetched.answer)
        }else{
            intentObj= Intent(this@SplashActivity , MainActivity::class.java)
        }
        startActivity(intentObj)
        finish()
    }

    companion object {
       private val AUTO_HIDE_DELAY_MILLIS = 1000L
    }

    override fun fetchSecurityQstnListener(securityQuestion: SecurityQuestionModel?, operation: SettingsActivity.OPERATION) {
        try {
            if (securityQuestion != null) {
                mSecurityFetched =  securityQuestion!!
            }
        }catch (ex:Exception){
        }
    }
}
