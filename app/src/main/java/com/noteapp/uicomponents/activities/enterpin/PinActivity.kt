package com.noteapp.uicomponents.activities.enterpin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager

import com.noteapp.uicomponents.base.BaseActivity
import com.noteapp.uicomponents.common.PinEntryEditText
import kotlinx.android.synthetic.main.activity_note.*
import android.text.Editable
import android.text.TextWatcher
import com.noteapp.R
import android.app.Activity
import android.view.View
import android.widget.Button
import com.noteapp.uicomponents.activities.settings.SecurityResolutionDialog


class PinActivity : BaseActivity(), View.OnClickListener , SecurityResolutionDialog.UserEnteredAnswer {
    lateinit var mOtpEditText : PinEntryEditText
    lateinit var mInputMethodManager : InputMethodManager
    lateinit var mSubmit : Button
    val resultIntent = Intent()
    var mPreviousPIN = -1
    lateinit var mPreviousQSTN : String
    lateinit var mPreviousANSWER : String
    var mWrongTrialCount = 0
    val TAG = "PinActivity"
    lateinit var mSubmittedPIN : String
    lateinit var mSecretQstnDialog : SecurityResolutionDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.enter_pin)
        setSupportActionBar(actionBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mPreviousPIN = intent.getIntExtra("LAST_PIN",-1)
        mPreviousQSTN = intent.getStringExtra("QUESTION")
        mPreviousANSWER = intent.getStringExtra("ANSWER")

        mSecretQstnDialog =  SecurityResolutionDialog(mPreviousQSTN,mPreviousANSWER,this)
        mAppLogger.debug(TAG,"mPreviousPIN === $mPreviousPIN")
        mAppLogger.debug(TAG,"mPreviousANSWER === $mPreviousANSWER")


        mSubmit = findViewById(R.id.btnSubmit)
        mSubmit.setOnClickListener(this)
        mSubmittedPIN = ""
        mOtpEditText = findViewById(R.id.otpView)
        mOtpEditText.requestFocus()
        mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        mOtpEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence,
                                           start: Int,
                                           count: Int,
                                           after: Int) {
            }

            override fun onTextChanged(s: CharSequence,
                                       start: Int,
                                       before: Int,
                                       count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length == 4) {
                    mInputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                    //sendDataBack(s.toString())
                    //compareData(s.toString())
                    mSubmittedPIN = s.toString()
                }
            }
        })
    }


    override fun onClick(v: View?) {
        if(v!!.id == R.id.btnSubmit){

            compareData(mSubmittedPIN)

        }
    }

    fun compareData(data:String){
        if(!data.isEmpty()){
            if(mPreviousPIN.compareTo(data.toInt())==0){
                //PIN Success
                showToast("Success")
            }else {
                mWrongTrialCount ++

                if(mWrongTrialCount < 3)
                {
                    showToast("Wrong PIN entered! Pls try again")
                    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                }
            }

            if(mWrongTrialCount >= 3){
                //showToast("Three times wrong data")
                //show the dialog with forgot password option
                mInputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                val fragmentTransitionImpl = supportFragmentManager.beginTransaction()
                val previousInstance = supportFragmentManager.findFragmentByTag("QSTNFRAG")
                if(previousInstance != null){
                    fragmentTransitionImpl.remove(previousInstance)
                }
                fragmentTransitionImpl.addToBackStack(null)

                mSecretQstnDialog.show(fragmentTransitionImpl,"QSTNFRAG")
            }
        }
    }
/*
    fun sendDataBack(data:String){

        if(!data.isEmpty()){
            resultIntent.putExtra(PIN_ENTERED,data.toInt())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }*/

    override fun userEnteredCorrectInput(status:Boolean) {
        if(status){
            resultIntent.putExtra(PIN_ENTERED_STATUS,status)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }


    companion object {
        val PIN_ENTERED_STATUS = "USER_PIN"
    }

    override fun onSupportNavigateUp(): Boolean {
        mInputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        setResult(Activity.RESULT_CANCELED)
        finish()
        return true
    }
}