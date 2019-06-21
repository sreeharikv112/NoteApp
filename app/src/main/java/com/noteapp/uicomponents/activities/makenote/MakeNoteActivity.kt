package com.noteapp.uicomponents.activities.makenote

import android.os.Bundle
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.noteapp.R
import com.noteapp.models.AddNoteViewModel
import com.noteapp.models.NoteModel
import com.noteapp.models.UpdateNoteViewModel
import com.noteapp.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*

import kotlinx.android.synthetic.main.content_make_note.*

class MakeNoteActivity : BaseActivity() ,View.OnClickListener {

    private var mTag:String= MakeNoteActivity::class.java.simpleName
    private var mAddNoteModel: AddNoteViewModel? = null
    private var mEditNoteModel : UpdateNoteViewModel? = null
    private var isEditNote : Boolean = false
    private var mNoteModel: NoteModel? = null
    private var mNoteTitle : TextInputEditText? = null
    private var mNoteDesc : TextInputEditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.create_note)
        fab_make_note.setOnClickListener(this)
        mAddNoteModel = AddNoteViewModel(application)
        mEditNoteModel = UpdateNoteViewModel(application)

        mNoteTitle = findViewById(R.id.addNoteTitle)
        mNoteDesc = findViewById(R.id.addNoteDescription)

        mNoteModel = intent.extras?.getParcelable<NoteModel>("selectedNote")
        isEditNote = intent.getBooleanExtra("editAction",false)

        if (isEditNote && mNoteModel != null) {

            mNoteTitle?.setText(mNoteModel?.noteTitle)
            mNoteDesc?.setText(mNoteModel?.noteDescription)
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.fab_make_note ->{
                    if (!mAppUtils.isInputEditTextFilled(addNoteTitle!!, addNoteLayout!!, getString(R.string.note_title_error))) {
                        return
                    }
                    else if (!mAppUtils.isInputEditTextFilled(addNoteDescription!!, addNoteDescriptionLayout!!, getString(R.string.create_note_error))) {
                        return
                    }
                    else {
                        mAppLogger.debug(mTag,"Proceed with saving to DB ")

                        if(isEditNote){
                            mNoteModel?.noteTitle = addNoteTitle.text.toString()
                            mNoteModel?.noteDescription = addNoteDescription.text.toString()
                            mNoteModel?.let { mEditNoteModel?.updateNote(it) }
                        }
                        else {
                            var noteModel = NoteModel()
                            noteModel.noteTitle = addNoteTitle.text.toString()
                            noteModel.noteDescription = addNoteDescription.text.toString()
                            mAddNoteModel?.addNote(noteModel)
                        }
                        closeActivity()
                    }
            }
        }
    }

    private fun closeActivity(){
        finish()
    }
}
