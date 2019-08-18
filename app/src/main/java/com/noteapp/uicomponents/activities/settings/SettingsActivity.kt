package com.noteapp.uicomponents.activities.settings

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.noteapp.R
import com.noteapp.models.SecurityQuestionModel
import com.noteapp.models.SecurityQuestionViewModel
import com.noteapp.uicomponents.activities.enterpin.PinActivity
import com.noteapp.uicomponents.activities.setuppin.IGetSecurityQuestionListener
import com.noteapp.uicomponents.activities.setuppin.SetupSecurity
import com.noteapp.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import java.lang.Exception

class SettingsActivity : BaseActivity() , CompoundButton.OnCheckedChangeListener, View.OnClickListener,
        IGetSecurityQuestionListener {


    lateinit var mUpdatePIN: MaterialButton
    lateinit var mAskPIN: SwitchMaterial
    lateinit var securityQstnVM : SecurityQuestionViewModel
    val TASK_ENTER_PIN = 10
    var mPreviousPIN : Int = -1
    val mTag = "SettingsActivity"


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
        mAskPIN = findViewById(R.id.askPIN)
        mAskPIN.setOnCheckedChangeListener(this)

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
      when(buttonView!!.id){
          /*R.id.updatePIN -> {
                if(isChecked){
                    //If pin already se, show pin activity and after cross verifying, go to SetupSecurity
                    securityQstnVM.getSecurityQuestion(this)

                }
          }*/


          R.id.askPIN -> {

          }
      }
    }


    override fun fetchSecurityQstnListener(securityQuestion: SecurityQuestionModel?) {
        try {
            if(securityQuestion != null) {
                mAppLogger.debug(mTag, "securityQuestion != null ===")
                mPreviousPIN = (securityQuestion.key).toInt()
                mAppLogger.debug(mTag, "mPreviousPIN = $mPreviousPIN")
                val intent = Intent(this,PinActivity::class.java)
                intent.putExtra("LAST_PIN",mPreviousPIN)
                intent.putExtra("QUESTION",securityQuestion.question)
                intent.putExtra("ANSWER",securityQuestion.answer)
                startActivity(intent)
                //startActivityForResult(intent,TASK_ENTER_PIN)
            }
            else{
                showSecurityActivity()
            }
        }catch(err: Exception){
            showSecurityActivity()
        }
    }

    fun showSecurityActivity(){
        startActivity(Intent(this@SettingsActivity, SetupSecurity::class.java))
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.updatePIN){
            securityQstnVM.getSecurityQuestion(this)
        }
    }

    fun demo(){
        /*var dialog =  SecurityResolutionDialog()
        dialog.show(supportFragmentManager,"")*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TASK_ENTER_PIN) {
            if (resultCode == Activity.RESULT_OK) {
                val pinEntered = data?.getIntExtra(PinActivity.PIN_ENTERED,-1)
                mAppLogger.debug(mTag,"pinEntered = $pinEntered")
                pinEntered?.let {

                    if(mPreviousPIN.compareTo(pinEntered)==0){
                        showSecurityActivity()
                    }else{
                        showAlert(R.string.pin_does_not_match,
                                R.string.ok,R.string.cancel,
                                DialogInterface.OnClickListener { dialog, which ->
                                    securityQstnVM.getSecurityQuestion(this)
                                },
                                DialogInterface.OnClickListener { dialog, which ->

                                })
                    }

                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}