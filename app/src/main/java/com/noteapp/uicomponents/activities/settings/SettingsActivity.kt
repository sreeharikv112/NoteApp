package com.noteapp.uicomponents.activities.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.noteapp.R
import com.noteapp.uicomponents.activities.setuppin.SetupSecurity
import com.noteapp.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*

class SettingsActivity : BaseActivity() , CompoundButton.OnCheckedChangeListener, View.OnClickListener{

    lateinit var mUpdatePIN: MaterialButton
    lateinit var mAskPIN: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.settings)
        setSupportActionBar(actionBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mUpdatePIN = findViewById(R.id.updatePIN)
        mUpdatePIN.setOnClickListener(this)
        mAskPIN = findViewById(R.id.askPIN)
        mAskPIN.setOnCheckedChangeListener(this)

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
      when(buttonView!!.id){
          R.id.updatePIN -> {
                if(isChecked){

                }
          }
          R.id.askPIN -> {

          }
      }
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.updatePIN){
            startActivity(Intent(this@SettingsActivity,SetupSecurity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}