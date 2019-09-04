package com.noteapp.uicomponents.activities.makenote

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.noteapp.R
import com.noteapp.common.Constants

import com.noteapp.models.AddNoteViewModel
import com.noteapp.models.NoteModel
import com.noteapp.models.UpdateNoteViewModel
import com.noteapp.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*

import kotlinx.android.synthetic.main.content_make_note.*
import java.util.*




class MakeNoteActivity : BaseActivity() ,View.OnClickListener {

    private var mTag:String= MakeNoteActivity::class.java.simpleName
    private var mAddNoteModel: AddNoteViewModel? = null
    private var mEditNoteModel : UpdateNoteViewModel? = null
    private var isEditNote : Boolean = false
    private var mNoteModel: NoteModel? = null
    private var mNoteTitle : TextInputEditText? = null
    private var mNoteDesc : TextInputEditText? = null
    private var mBtnRed : MaterialButton? = null
    private var mBtnGreen : MaterialButton? = null
    private var mBtnBlue : MaterialButton? = null
    private var mBtnYellow : MaterialButton? = null
    private var mBtnDefault : MaterialButton? = null
    private var mNoteColor = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.create_note)
        setSupportActionBar(actionBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        fab_make_note.setOnClickListener(this)
        mAddNoteModel = AddNoteViewModel(application)
        mEditNoteModel = UpdateNoteViewModel(application)

        mNoteTitle = findViewById(R.id.addNoteTitle)
        mNoteDesc = findViewById(R.id.addNoteDescription)

        mNoteModel = intent.extras?.getSerializable(Constants.SELECTED_NOTE) as NoteModel?
        isEditNote = intent.getBooleanExtra(Constants.EDIT_ACTION,false)

        if (isEditNote && mNoteModel != null) {
            actionBar!!.title = getString(R.string.edit_note)
            mNoteTitle?.setText(mNoteModel?.noteTitle)
            mNoteDesc?.setText(mNoteModel?.noteDescription)
            mNoteColor = mNoteModel!!.noteColor
        }
        else{
            mNoteColor = -1
        }

        mBtnRed = findViewById(R.id.btnRed)
        mBtnRed!!.backgroundTintList = ContextCompat.getColorStateList(this@MakeNoteActivity, android.R.color.holo_red_light)
        mBtnRed!!.setOnClickListener(this)

        mBtnGreen = findViewById(R.id.btnGreen)
        mBtnGreen!!.backgroundTintList = ContextCompat.getColorStateList(this@MakeNoteActivity, android.R.color.holo_green_light)
        mBtnGreen!!.setOnClickListener(this)

        mBtnBlue = findViewById(R.id.btnBlue)
        mBtnBlue!!.backgroundTintList = ContextCompat.getColorStateList(this@MakeNoteActivity, android.R.color.holo_blue_light)
        mBtnBlue!!.setOnClickListener(this)

        mBtnYellow = findViewById(R.id.btnYellow)
        mBtnYellow!!.backgroundTintList = ContextCompat.getColorStateList(this@MakeNoteActivity, android.R.color.holo_orange_light)
        mBtnYellow!!.setOnClickListener(this)

        mBtnDefault = findViewById(R.id.btnDefault)
        mBtnDefault!!.backgroundTintList = ContextCompat.getColorStateList(this@MakeNoteActivity, android.R.color.transparent)
        mBtnDefault!!.setOnClickListener(this)

        mNoteTitle!!.requestFocus()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflator = menuInflater
        inflator.inflate(R.menu.create_note_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId)
        {
            R.id.action_save ->
                validateAndSaveNote()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.fab_make_note ->
                validateAndSaveNote()

            R.id.btnRed ->
            {
                mNoteColor = 1
                showToast("RED color selected")
            }

            R.id.btnGreen ->
            {
                mNoteColor = 2
                showToast("GREEN color selected")
            }

            R.id.btnBlue ->
            {
                mNoteColor = 3
                showToast("BLUE color selected")
            }

            R.id.btnYellow ->
            {
                mNoteColor = 4
                showToast("YELLOW color selected")
            }

            R.id.btnDefault ->
            {
                mNoteColor = -1
                showToast("DEFAULT color selected")
            }

            else -> {
                mNoteColor = -1
            }
        }
    }

    private fun validateAndSaveNote(){
        if (!mAppUtils.isInputEditTextFilled(addNoteTitle!!, addNoteLayout!!, getString(R.string.note_title_error))) {
            return
        }
        /*else if (!mAppUtils.isInputEditTextFilled(addNoteDescription!!, addNoteDescriptionLayout!!, getString(R.string.create_note_error))) {
            return
        }*/
        else {
            mAppLogger.debug(mTag,"Proceed with saving to DB ")

            if(isEditNote){
                mNoteModel?.noteTitle = addNoteTitle.text.toString()
                mNoteModel?.noteDescription = addNoteDescription.text.toString()
                mNoteModel?.dateSaved = Date()
                mNoteModel?.noteColor = mNoteColor
                mNoteModel?.let { mEditNoteModel?.updateNote(it) }
            }
            else {
                var noteModel = NoteModel(null,addNoteTitle.text.toString(),
                        addNoteDescription.text.toString(),
                        Date(),mNoteColor)

                mAddNoteModel?.addNote(noteModel)
            }
            closeActivity()
        }
    }

    private fun closeActivity(){
        finish()
    }
}
