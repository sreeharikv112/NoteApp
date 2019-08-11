package com.noteapp.uicomponents.activities.enterpin

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager

import com.noteapp.uicomponents.base.BaseActivity
import com.noteapp.uicomponents.common.PinEntryEditText
import kotlinx.android.synthetic.main.activity_note.*
import android.text.Editable
import android.text.TextWatcher
import com.noteapp.R


class PinActivity : BaseActivity() {

    lateinit var mOtpEditText : PinEntryEditText
    lateinit var mInputMethodManager : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.create_pin)
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
                }
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}