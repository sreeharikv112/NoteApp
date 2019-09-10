package com.noteapp.uicomponents.activities.setuppin

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.NestedScrollView
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
,IUpdateSecurityListener , CompoundButton.OnCheckedChangeListener /*, RadioGroup.OnCheckedChangeListener*/
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
    lateinit var mFingerPrintSelected : RadioButton
    lateinit var mPINSelected : RadioButton
    lateinit var mPINComponentLayout : NestedScrollView
    lateinit var mEdtUserAnswer : TextInputEditText
    lateinit var mEdtInputLayout: TextInputLayout
    lateinit var mRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.setup_security)
        setSupportActionBar(actionBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        securityQstnVM = SecurityQuestionViewModel(application)
        mSharedPrefHelper = SharedPreferenceHelper(this)

        //New changes.....
        mRadioGroup = findViewById(R.id.radioGroupOption)
        mFingerPrintSelected = findViewById(R.id.finderPrintOption)
        mPINSelected = findViewById(R.id.pinOption)
        //mFingerPrintSelected.setOnCheckedChangeListener(this)
        //mPINSelected.setOnCheckedChangeListener(this)
        mPINComponentLayout = findViewById(R.id.pin_content_layout)
        mRadioGroup.setOnCheckedChangeListener { group, checkedId ->

            if(checkedId == R.id.finderPrintOption){
                mPINComponentLayout.visibility = View.INVISIBLE
            }else {
                setupPINComponents()
            }
        }

        ///////////////
        mSwitchAskPIN = findViewById(R.id.askPIN)
        mSwitchAskPIN.setOnCheckedChangeListener(this)
        mPin = findViewById(R.id.newPin)
        mPinConfirm = findViewById(R.id.newPinConfirm)
        mSpinnerQstnOne = findViewById(R.id.securityQOne)
        mUserAnswer = findViewById(R.id.edtUserAnswer)
        mSaveButton = findViewById(R.id.btnSave)
        mEdtUserAnswer = findViewById(R.id.edtUserAnswer)
        mEdtInputLayout = findViewById(R.id.addAnswerLayout)

        mSaveButton.setOnClickListener(this)
        mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    }

    /*override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

        if(mFingerPrintSelected.isChecked){
            mPINComponentLayout.visibility = View.INVISIBLE
        }else if (mPINSelected.isChecked){
            setupPINComponents()
        }
    }*/

    private fun setupPINComponents() {

        mPINComponentLayout.visibility = View.VISIBLE
        //Old flow
        mQuestionOne.add("Please Select")
        mQuestionOne.add("Name of first Pet")
        mQuestionOne.add("Favourite destination")
        mQuestionOne.add("Name of first Car")
        mQuestionOne.add("First job")



        mKeyRequiredStatus = mSharedPrefHelper.getBoolData(Constants.KEY_PIN_REQUIRED)
        try {
            mShouldSetKeyRequired = intent.getBooleanExtra(Constants.KEY_PIN_REQUIRED,false)
        } catch (e: Exception) {
        }

        mSelectedSecurityQuestion = mQuestionOne.first()



        var questionOneAdapter  = ArrayAdapter(this,android.R.layout.simple_spinner_item,
                mQuestionOne)
        questionOneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mSpinnerQstnOne.adapter = questionOneAdapter
        mSpinnerQstnOne.onItemSelectedListener = this

        if(mKeyRequiredStatus){
            mSwitchAskPIN.isChecked = true
        }


        mPin.requestFocus()
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        //Text Change Listeners
        mPin.addTextChangedListener(object : TextWatcher {
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
                    mPinConfirm.requestFocus()
                }
            }
        })

        mPinConfirm.addTextChangedListener(object : TextWatcher {
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
                    mSpinnerQstnOne.requestFocus()
                    mSpinnerQstnOne.performClick()
                }
            }
        })
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
            validateFields(false)
        }
    }

    private fun validateFields(isOnCheckedChanged: Boolean): Boolean{
        var status = true

        if( TextUtils.isEmpty(mPin.text) || mPin.text?.length !=4){
            showToast(getString(R.string.please_enter_valid_pin))
            mPin.requestFocus()
            mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            status = false
        }
        else if(TextUtils.isEmpty(mPinConfirm.text) || mPinConfirm.text?.length !=4){
            showToast(getString(R.string.please_enter_confirm_pin))
            mPinConfirm.requestFocus()
            mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            status = false
        }
        else if( !(mPin.text.toString() == mPinConfirm.text.toString())!!){
            showToast(getString(R.string.pin_and_confirm_pin_shouldmatch))
            mPinConfirm.requestFocus()
            mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            status = false
        }
        else if(mSelectedSecurityQuestion == mQuestionOne[0]){
            status = false
            mSpinnerQstnOne.performClick()
        }
        else if (!mAppUtils.isInputEditTextFilled(mEdtUserAnswer!!, mEdtInputLayout!!, getString(R.string.enter_security_answer))) {
            mUserAnswer.requestFocus()
            mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            status = false
        }
        else if(mEdtUserAnswer.text!!.length < 4){
            showToast(getString(R.string.answer_min_chars))
            mEdtUserAnswer.requestFocus()
            mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

            status = false
        }
        else if(isOnCheckedChanged){
            status = true
            mSwitchAskPIN.isChecked = true
        }
        else{
            status = true
            mAppLogger.debug(mTag,"calling getSecurityQuestion!!")
            mInputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, InputMethodManager.HIDE_IMPLICIT_ONLY)
            securityQstnVM.getSecurityQuestion(this, SettingsActivity.OPERATION.NONE)
        }
        return status
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if(!isChecked){
            mSharedPrefHelper.saveBoolData(Constants.KEY_PIN_REQUIRED,false)
            mSwitchAskPIN.isChecked = false
            onSupportNavigateUp()
            showToast(getString(R.string.pin_will_not_be_asked_at_start))
        }
        else if(  mKeyRequiredStatus && !isChecked){
            mSwitchAskPIN.isChecked = false
            mSharedPrefHelper.saveBoolData(Constants.KEY_PIN_REQUIRED,false)
            showToast(getString(R.string.pin_will_not_be_asked_at_start))
        }
        else if(mKeyRequiredStatus && isChecked){
            mSwitchAskPIN.isChecked = true
        }
        else if(!mKeyRequiredStatus && isChecked){
            //check pin and set status
            pinCheckPendingToSetToTrue = true
            //validateFields()
            //securityQstnVM.getSecurityQuestion(this, SettingsActivity.OPERATION.SWITCH_CHANGED)
            mSwitchAskPIN.isChecked = false
            validateFields(true)
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
                        pinCheckPendingToSetToTrue = false
                        mSwitchAskPIN.isChecked = true
                        mSharedPrefHelper.saveBoolData(Constants.KEY_PIN_REQUIRED,true)
                        onSupportNavigateUp()
                    }
                    else{
                        showToast(getString(R.string.please_setup_pin))
                        mSwitchAskPIN.isChecked = false
                        mAppLogger.debug(mTag,"mSecurityData.value is null ")
                        mPin.requestFocus()
                        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
                    }
                }catch(err: Exception){
                    showToast(getString(R.string.please_setup_pin))
                    mSwitchAskPIN.isChecked = false
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
                            mEdtUserAnswer.text.toString())

                    securityQstnVM.insertOrUpdateSecurityQuestion(securityQstn,this)

                    mAppLogger.debug(mTag,"Completed !!!")
                }
            }
        }
    }

    override fun didSecurityQuestionUpdated(status: Boolean) {
        if(status){
            if(pinCheckPendingToSetToTrue)
            {
                pinCheckPendingToSetToTrue = false
                mSharedPrefHelper.saveBoolData(Constants.KEY_PIN_REQUIRED,true)
            }
            showToast(getString(R.string.security_settings_updated))
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