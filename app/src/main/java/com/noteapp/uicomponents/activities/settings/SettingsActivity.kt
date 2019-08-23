package com.noteapp.uicomponents.activities.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.noteapp.R
import com.noteapp.common.Constants.Companion.KEY_PIN_REQUIRED
import com.noteapp.db.SharedPreferenceHelper
import com.noteapp.models.SecurityQuestionModel
import com.noteapp.models.SecurityQuestionViewModel
import com.noteapp.uicomponents.activities.enterpin.PinActivity
import com.noteapp.uicomponents.activities.setuppin.IGetSecurityQuestionListener
import com.noteapp.uicomponents.activities.setuppin.SetupSecurity
import com.noteapp.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import java.lang.Exception

class SettingsActivity : BaseActivity() /*, CompoundButton.OnCheckedChangeListener*/, View.OnClickListener,
        IGetSecurityQuestionListener {

    lateinit var mUpdatePIN: MaterialButton
    /*lateinit var mSwitchAskPIN: SwitchMaterial*/
    lateinit var securityQstnVM : SecurityQuestionViewModel
    val TASK_ENTER_PIN = 10
    var mPreviousPIN : Int = -1
    val mTag = "SettingsActivity"
    var mKeyRequiredStatus = false
    lateinit var mSharedPrefHelper : SharedPreferenceHelper
    var mCheckedState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.settings)
        setSupportActionBar(actionBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        securityQstnVM = SecurityQuestionViewModel(application)
        mUpdatePIN = findViewById(R.id.updatePIN)
        mUpdatePIN.setOnClickListener(this)
        /*mSwitchAskPIN = findViewById(R.id.askPIN)
        mSwitchAskPIN.setOnCheckedChangeListener(this)*/
        mSharedPrefHelper = SharedPreferenceHelper(this)
        mKeyRequiredStatus = mSharedPrefHelper.getBoolData(KEY_PIN_REQUIRED)
        /*if(mKeyRequiredStatus){
            mSwitchAskPIN.isChecked = true
        }*/
    }

    /*override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
      when(buttonView!!.id){
          R.id.askPIN -> {
              if(  !mSharedPrefHelper.getBoolData(KEY_PIN_REQUIRED) && isChecked){
                  mCheckedState = true
                  securityQstnVM.getSecurityQuestion(this,OPERATION.SWITCH_CHANGED)
                  mSwitchAskPIN.isChecked = false
              }
              else if(mSharedPrefHelper.getBoolData(KEY_PIN_REQUIRED)){
                  mSwitchAskPIN.isChecked = true
              }
              else{
                  mSharedPrefHelper.saveBoolData(KEY_PIN_REQUIRED,false)
                  mSwitchAskPIN.isChecked = false
              }
          }
      }
    }*/

    override fun fetchSecurityQstnListener(securityQuestion: SecurityQuestionModel?, operation: OPERATION) {
        try {
            if(securityQuestion != null) {
                mAppLogger.debug(mTag, "securityQuestion != null ===")
                mPreviousPIN = (securityQuestion.key)
                mAppLogger.debug(mTag, "mPreviousPIN = $mPreviousPIN")

                /*if(operation == OPERATION.SWITCH_CHANGED){
                    if(mCheckedState){
                        mSharedPrefHelper.saveBoolData(KEY_PIN_REQUIRED,true)
                        mSwitchAskPIN.isChecked = true
                    }else{
                        mSharedPrefHelper.saveBoolData(KEY_PIN_REQUIRED,false)
                        mSwitchAskPIN.isChecked = false
                    }
                }else{*/
                    val intent = Intent(this,PinActivity::class.java)
                    intent.putExtra("LAST_PIN",mPreviousPIN)
                    intent.putExtra("QUESTION",securityQuestion.question)
                    intent.putExtra("ANSWER",securityQuestion.answer)
                    startActivityForResult(intent,TASK_ENTER_PIN)
                /*}*/
            }
            else{
                showSecurityActivity()
            }
        }catch(err: Exception){
            showSecurityActivity()
        }
    }

    fun showSecurityActivity(){
        val intent = Intent(this@SettingsActivity, SetupSecurity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.updatePIN){
            securityQstnVM.getSecurityQuestion(this,OPERATION.BUTTON_CLICKED)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TASK_ENTER_PIN) {
            if (resultCode == Activity.RESULT_OK) {
                val pinEntered = data?.getBooleanExtra(PinActivity.PIN_ENTERED_STATUS,false)
                mAppLogger.debug(mTag,"pinEntered = $pinEntered")
                pinEntered?.let {
                    if(pinEntered){
                        showSecurityActivity()
                    }
                }
            }
        }
    }

    enum class OPERATION {
        SWITCH_CHANGED, BUTTON_CLICKED , NONE
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}