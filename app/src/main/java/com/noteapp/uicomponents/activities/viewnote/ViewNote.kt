package com.noteapp.uicomponents.activities.viewnote

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView

import com.noteapp.models.NoteModel
import com.noteapp.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import android.R



class ViewNote : BaseActivity() {

    private var mNoteModel: NoteModel? = null
    var textNoteHead : TextView? = null
    var textNoteDesc : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.noteapp.R.layout.activity_view_note)
        val actionBar = toolbar
        actionBar!!.title = getString(com.noteapp.R.string.view_note)

        mNoteModel = intent.extras!!.getParcelable<NoteModel>("selectedNote")

        val noteTitle = mNoteModel?.noteTitle
        val noteDesc = mNoteModel?.noteDescription

        textNoteHead = findViewById(com.noteapp.R.id.noteHead)
        textNoteDesc = findViewById(com.noteapp.R.id.noteContent)

        if (!TextUtils.isEmpty(noteTitle)){

            textNoteHead?.text = noteTitle

            if (!TextUtils.isEmpty(noteTitle)){
                textNoteDesc?.text = noteDesc
            }
        }

    }
}
