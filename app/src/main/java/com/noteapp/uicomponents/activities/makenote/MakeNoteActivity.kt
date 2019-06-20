package com.noteapp.uicomponents.activities.makenote

import android.os.Bundle
import android.view.View
import com.noteapp.R
import com.noteapp.models.AddNoteViewModel
import com.noteapp.models.NoteModel
import com.noteapp.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*

import kotlinx.android.synthetic.main.content_make_note.*

class MakeNoteActivity : BaseActivity() ,View.OnClickListener {

    private var mTag:String= MakeNoteActivity::class.java.simpleName
    private lateinit var mAddNoteModel: AddNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.create_note)
        fab_make_note.setOnClickListener(this)
        mAddNoteModel = AddNoteViewModel(application)

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
                        var noteModel =  NoteModel()
                        noteModel.noteTitle=addNoteTitle.text.toString()
                        noteModel.noteDescription=addNoteDescription.text.toString()
                        mAddNoteModel.addNote(noteModel)
                        closeActivity()
                    }
            }
        }
    }

    private fun closeActivity(){
        finish()
    }
}
