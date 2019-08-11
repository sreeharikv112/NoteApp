package com.noteapp.uicomponents.activities.viewnote

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.noteapp.models.NoteModel
import com.noteapp.uicomponents.activities.makenote.MakeNoteActivity
import com.noteapp.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*


class ViewNote : BaseActivity() {

    private var mNoteModel: NoteModel? = null
    var textNoteHead : TextView? = null
    var textNoteDesc : TextView? = null
    var linearBackground : LinearLayoutCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.noteapp.R.layout.activity_view_note)
        val actionBar = toolbar
        actionBar!!.title = getString(com.noteapp.R.string.view_note)
        setSupportActionBar(actionBar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        linearBackground = findViewById(com.noteapp.R.id.contentBackground)

        mNoteModel = intent.extras!!.getSerializable("selectedNote") as NoteModel

        val noteTitle = mNoteModel?.noteTitle
        val noteDesc = mNoteModel?.noteDescription

        textNoteHead = findViewById(com.noteapp.R.id.noteHead)
        textNoteDesc = findViewById(com.noteapp.R.id.noteContent)

        if (mNoteModel!=null && !TextUtils.isEmpty(noteTitle)){

            textNoteHead?.text = noteTitle

            if (!TextUtils.isEmpty(noteTitle)){
                textNoteDesc?.text = noteDesc
            }

            val background = mNoteModel!!.noteColor

            if(background != -1){

                textNoteHead!!.setTextColor(resources.getColor(R.color.white))
                textNoteDesc!!.setTextColor(resources.getColor(R.color.white))

                when(background){
                    1 ->
                        linearBackground!!.setBackgroundColor(resources.getColor(R.color.holo_red_light))
                    2 ->
                        linearBackground!!.setBackgroundColor(resources.getColor(R.color.holo_green_light))
                    3 ->
                        linearBackground!!.setBackgroundColor(resources.getColor(R.color.holo_blue_light))
                    4 ->
                        linearBackground!!.setBackgroundColor(resources.getColor(R.color.holo_orange_light))

                    else -> {
                        textNoteHead!!.setTextColor(resources.getColor(R.color.black))
                        textNoteDesc!!.setTextColor(resources.getColor(R.color.darker_gray))
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
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
        //first close this view
        finish()
        //open new edit activity
        val intent = Intent(this@ViewNote, MakeNoteActivity::class.java)
        intent.putExtra("selectedNote",mNoteModel)
        intent.putExtra("editAction",true)
        startActivity(intent)
    }
}
