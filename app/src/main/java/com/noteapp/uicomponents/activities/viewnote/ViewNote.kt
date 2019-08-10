package com.noteapp.uicomponents.activities.viewnote

import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView

import com.noteapp.models.NoteModel
import com.noteapp.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import android.R
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.noteapp.uicomponents.activities.makenote.MakeNoteActivity


class ViewNote : BaseActivity() {

    private var mNoteModel: NoteModel? = null
    var textNoteHead : TextView? = null
    var textNoteDesc : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.noteapp.R.layout.activity_view_note)
        val actionBar = toolbar
        actionBar!!.title = getString(com.noteapp.R.string.view_note)
        setSupportActionBar(actionBar)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var inflator = menuInflater
        inflator.inflate(com.noteapp.R.menu.edit_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId)
        {
            com.noteapp.R.id.action_edit ->
            {
                showEditNote()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun showEditNote() {
        val intent = Intent(this@ViewNote, MakeNoteActivity::class.java)
        intent.putExtra("selectedNote",mNoteModel)
        intent.putExtra("editAction",true)
        startActivity(intent)
    }
}
