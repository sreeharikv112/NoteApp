package com.noteapp.uicomponents.activities.landing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noteapp.R
import com.noteapp.models.NoteListViewModel
import com.noteapp.models.NoteModel
import com.noteapp.uicomponents.activities.makenote.MakeNoteActivity
import com.noteapp.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnLongClickListener {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mNoteAdapter: NoteAdapter
    private lateinit var mNoteListViewModel: NoteListViewModel
    private lateinit var mNoteModel: NoteModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mRecyclerView = findViewById(R.id.listOfNoteRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mNoteAdapter = NoteAdapter(noteList = ArrayList<NoteModel>(), longClickListener = this)
        mRecyclerView.adapter = mNoteAdapter

        fab.setOnClickListener { view ->
            invokeNewNoteActivity()
        }

        mNoteListViewModel = ViewModelProviders.of(this).get(NoteListViewModel::class.java)
        mNoteListViewModel.mNoteList.observe(this@MainActivity, Observer { noteModels -> mNoteAdapter.addNote(noteModels) })

    }

    fun invokeNewNoteActivity() {
        val intent = Intent(this@MainActivity, MakeNoteActivity::class.java)
        startActivity(intent)
    }

    override fun onLongClick(v: View?): Boolean {
        mNoteModel = v?.getTag() as NoteModel
        showAlert(getString(R.string.delete_confirmation), R.string.ok, R.string.cancel)
        return true
    }

    override fun handlePositiveAlertCallBack() {
        mNoteListViewModel.deleteNote(mNoteModel)
        showToast(getString(R.string.note_deleted))
    }

}
