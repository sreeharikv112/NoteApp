package com.noteapp.uicomponents.activities.setuppin

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.noteapp.R
import com.noteapp.uicomponents.base.BaseActivity
import com.noteapp.uicomponents.common.PinEntryEditText
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.content_make_note.*
import kotlinx.android.synthetic.main.content_security.*

class SetupSecurity: BaseActivity() , AdapterView.OnItemSelectedListener, View.OnClickListener{


    lateinit var mSpinnerQstnOne : AppCompatSpinner
    lateinit var mPin : PinEntryEditText
    lateinit var mPinConfirm : PinEntryEditText
    lateinit var mUserAnswer : TextInputEditText
    lateinit var mSaveButton : MaterialButton
    var mQuestionOne = mutableListOf<String>()
    val mTag = "SetupSecurity"
    lateinit var mSelectedSecurityQuestion : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.setup_security)
        setSupportActionBar(actionBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mQuestionOne.add("Name of first Pet")
        mQuestionOne.add("Favourite destination")
        mQuestionOne.add("Name of first Car")
        mQuestionOne.add("First job")

        mSelectedSecurityQuestion = mQuestionOne.first()

        mPin = findViewById(R.id.newPin)
        mPinConfirm = findViewById(R.id.newPinConfirm)
        mSpinnerQstnOne = findViewById(R.id.securityQOne)
        mUserAnswer = findViewById(R.id.edtUserAnswer)
        mSaveButton = findViewById(R.id.btnSave)
        mSaveButton.setOnClickListener(this)

        var questionOneAdapter  = ArrayAdapter(this,android.R.layout.simple_spinner_item,
                mQuestionOne)
        questionOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mSpinnerQstnOne.adapter = questionOneAdapter
        mSpinnerQstnOne.onItemSelectedListener = this
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mAppLogger.debug(mTag,"Selected item ${mQuestionOne[position]}")
        mSelectedSecurityQuestion = mQuestionOne[position]
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.btnSave){

            if( TextUtils.isEmpty(mPin.text) || mPin.text?.length !=4){
                showToast("Please enter valid PIN")
                mPin.requestFocus()
                return
            }
            else if(TextUtils.isEmpty(mPinConfirm.text) || mPinConfirm.text?.length !=4){
                showToast("Please enter valid confirm PIN")
                mPinConfirm.requestFocus()
                return
            }
            else if( !(mPin.text.toString() == mPinConfirm.text.toString())!!){
                showToast("PIN and Confirm PIN does not match")
                mPinConfirm.requestFocus()
                return
            }
            else if(mSelectedSecurityQuestion == mQuestionOne[0]){
                mSpinnerQstnOne.performClick()
            }
            else if (!mAppUtils.isInputEditTextFilled(edtUserAnswer!!, addAnswerLayout!!, getString(R.string.enter_security_answer))) {
                mUserAnswer.requestFocus()
                return
            }
            else if(edtUserAnswer.text!!.length < 4){
                showToast("Answer should be of 4 letter minimum")
                edtUserAnswer.requestFocus()

                return
            }
            else{
                showToast("Proceed!!!")
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}