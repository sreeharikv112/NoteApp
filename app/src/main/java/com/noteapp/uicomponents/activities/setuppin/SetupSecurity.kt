package com.noteapp.uicomponents.activities.setuppin

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import androidx.appcompat.widget.AppCompatSpinner
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.noteapp.R
import com.noteapp.common.Constants
import com.noteapp.db.SharedPreferenceHelper
import com.noteapp.models.SecurityQuestionModel
import com.noteapp.models.SecurityQuestionViewModel
import com.noteapp.uicomponents.activities.settings.SettingsActivity
import com.noteapp.uicomponents.base.BaseActivity
import com.noteapp.uicomponents.common.PinEntryEditText
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.content_security.*
import java.lang.Exception

class SetupSecurity: BaseActivity() , AdapterView.OnItemSelectedListener, View.OnClickListener,IGetSecurityQuestionListener
,IUpdateSecurityListener , CompoundButton.OnCheckedChangeListener
{
    private var pinCheckPendingToSetToTrue = false
    lateinit var mSpinnerQstnOne : AppCompatSpinner
    lateinit var mPin : PinEntryEditText
    lateinit var mPinConfirm : PinEntryEditText
    lateinit var mUserAnswer : TextInputEditText
    lateinit var mSaveButton : MaterialButton
    var mQuestionOne = mutableListOf<String>()
    val mTag = "SetupIGetSecurity"
    lateinit var mSwitchAskPIN: SwitchMaterial
    lateinit var mSelectedSecurityQuestion : String
    lateinit var securityQstnVM : SecurityQuestionViewModel
    /*lateinit var mSecurityData : SecurityQuestionModel*/
    var mShouldSetKeyRequired = false
    lateinit var mSharedPrefHelper : SharedPreferenceHelper
    var mKeyRequiredStatus = false
    lateinit var mInputMethodManager : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.setup_security)
        setSupportActionBar(actionBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        securityQstnVM = SecurityQuestionViewModel(application)
        mQuestionOne.add("Please Select")
        mQuestionOne.add("Name of first Pet")
        mQuestionOne.add("Favourite destination")
        mQuestionOne.add("Name of first Car")
        mQuestionOne.add("First job")
        mSwitchAskPIN = findViewById(R.id.askPIN)
        mSwitchAskPIN.setOnCheckedChangeListener(this)

        mSharedPrefHelper = SharedPreferenceHelper(this)
        mKeyRequiredStatus = mSharedPrefHelper.getBoolData(Constants.KEY_PIN_REQUIRED)
        try {
            mShouldSetKeyRequired = intent.getBooleanExtra("KEY_PIN_REQUIRED",false)
        } catch (e: Exception) {
        }

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

        if(mKeyRequiredStatus){
            mSwitchAskPIN.isChecked = true
        }
        mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

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
                mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                return
            }
            else if(TextUtils.isEmpty(mPinConfirm.text) || mPinConfirm.text?.length !=4){
                showToast("Please confirm PIN")
                mPinConfirm.requestFocus()
                mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                return
            }
            else if( !(mPin.text.toString() == mPinConfirm.text.toString())!!){
                showToast("PIN and Confirm PIN does not match")
                mPinConfirm.requestFocus()
                mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                return
            }
            else if(mSelectedSecurityQuestion == mQuestionOne[0]){
                mSpinnerQstnOne.performClick()
            }
            else if (!mAppUtils.isInputEditTextFilled(edtUserAnswer!!, addAnswerLayout!!, getString(R.string.enter_security_answer))) {
                mUserAnswer.requestFocus()
                mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                return
            }
            else if(edtUserAnswer.text!!.length < 4){
                showToast("Answer should be of minimum 4 character")
                edtUserAnswer.requestFocus()
                mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                return
            }
            else{
                mAppLogger.debug(mTag,"calling getSecurityQuestion!!")
                mInputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, InputMethodManager.HIDE_IMPLICIT_ONLY)
                securityQstnVM.getSecurityQuestion(this, SettingsActivity.OPERATION.NONE)
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if(  mKeyRequiredStatus && !isChecked){
            mSwitchAskPIN.isChecked = false
            mSharedPrefHelper.saveBoolData(Constants.KEY_PIN_REQUIRED,false)
            showToast("PIN will not be asked at start!")
        }
        else if(mKeyRequiredStatus && isChecked){
            mSwitchAskPIN.isChecked = true
        }
        else if(!mKeyRequiredStatus && isChecked){
            //check pin and set status
            pinCheckPendingToSetToTrue = true
            securityQstnVM.getSecurityQuestion(this, SettingsActivity.OPERATION.SWITCH_CHANGED)
        }
        else if(!mKeyRequiredStatus && !isChecked){
            mSwitchAskPIN.isChecked = false
        }
    }

    override fun fetchSecurityQstnListener(securityQuestion: SecurityQuestionModel?, operation: SettingsActivity.OPERATION) {
        when (operation){
            SettingsActivity.OPERATION.SWITCH_CHANGED -> {
                try {

                    if(securityQuestion != null) {
                        /*mSecurityData = securityQuestion
                        mAppLogger.debug(mTag,"mSecurityData.answer = ${mSecurityData.answer}")
                        mAppLogger.debug(mTag,"mSecurityData.key = ${mSecurityData.key}")
                        mAppLogger.debug(mTag,"mSecurityData.question = ${mSecurityData.question}")*/
                        pinCheckPendingToSetToTrue = false
                        mSharedPrefHelper.saveBoolData(Constants.KEY_PIN_REQUIRED,true)
                        onSupportNavigateUp()
                    }
                    else{
                        mAppLogger.debug(mTag,"mSecurityData.value is null ")
                        mPin.requestFocus()
                        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                    }
                }catch(err: Exception){
                    mAppLogger.error(mTag,"mSecurityData is null ")
                    mPin.requestFocus()
                    mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                }
            }
            SettingsActivity.OPERATION.NONE -> {
                securityQuestion.let {
                    var securityQstn = SecurityQuestionModel(1,
                            mPinConfirm.text.toString().toInt(),
                            mSelectedSecurityQuestion,
                            edtUserAnswer.text.toString())

                    securityQstnVM.insertOrUpdateSecurityQuestion(securityQstn,this)

                    mAppLogger.debug(mTag,"Completed !!!")
                }
            }
        }
    }

    override fun didSecurityQuestionUpdated(status: Boolean) {
        if(status){
            /*if(mShouldSetKeyRequired){
                mSharedPrefHelper = SharedPreferenceHelper(this)
                mSharedPrefHelper.saveBoolData(Constants.KEY_PIN_REQUIRED,mShouldSetKeyRequired)
            }*/

            if(pinCheckPendingToSetToTrue)
            {
                pinCheckPendingToSetToTrue = false
                mSharedPrefHelper.saveBoolData(Constants.KEY_PIN_REQUIRED,true)
            }
            showToast("Security Settings updated")
            finish()
        }else{
            showToast("Could not update Security settings. Pls try again.")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        mInputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, InputMethodManager.HIDE_IMPLICIT_ONLY)
        finish()
        return true
    }
}