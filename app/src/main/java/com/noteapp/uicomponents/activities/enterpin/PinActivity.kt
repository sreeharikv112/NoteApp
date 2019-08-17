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





class PinActivity : BaseActivity() {

    lateinit var mOtpEditText : PinEntryEditText
    lateinit var mInputMethodManager : InputMethodManager
    val resultIntent = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.enter_pin)
        setSupportActionBar(actionBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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
                    sendDataBack(s.toString())
                }
            }
        })
    }

    fun sendDataBack(data:String){

        if(!data.isEmpty()){

            resultIntent.putExtra(PIN_ENTERED,data.toInt())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    companion object {
        val PIN_ENTERED = "USER_PIN"
    }

    override fun onSupportNavigateUp(): Boolean {
        setResult(Activity.RESULT_CANCELED)
        finish()
        return true
    }
}