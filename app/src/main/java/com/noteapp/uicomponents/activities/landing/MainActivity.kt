package com.noteapp.uicomponents.activities.landing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noteapp.R
import com.noteapp.models.NoteListViewModel
import com.noteapp.models.NoteModel
import com.noteapp.uicomponents.activities.enterpin.PinActivity
import com.noteapp.uicomponents.activities.makenote.MakeNoteActivity
import com.noteapp.uicomponents.activities.settings.SettingsActivity
import com.noteapp.uicomponents.activities.viewnote.ViewNote
import com.noteapp.uicomponents.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {


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
        mNoteAdapter = NoteAdapter(this,noteList = ArrayList<NoteModel>(),
                onClickListener = this,
                onDeletePressed = DeleteBtnClick(),
                onEditPressed = EditBtnClick()
                )
        mRecyclerView.adapter = mNoteAdapter

        fab.setOnClickListener { view ->
            invokeNewNoteActivity()
        }

        mNoteListViewModel = ViewModelProviders.of(this).get(NoteListViewModel::class.java)
        mNoteListViewModel.mNoteList.observe(this@MainActivity, Observer { noteModels -> mNoteAdapter.addNote(noteModels) })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflator = menuInflater
        inflator.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId)
        {
            R.id.action_settings ->
                goToSettings()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToSettings() {
        startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
    }

    private fun invokeNewNoteActivity() {
        val intent = Intent(this@MainActivity, MakeNoteActivity::class.java)
        startActivity(intent)
    }


    override fun onClick(v: View?) {
        mNoteModel = v?.getTag() as NoteModel
        val intent = Intent(this@MainActivity, ViewNote::class.java)
        val bundle = Bundle()
        bundle.putSerializable("selectedNote",mNoteModel)
        intent.putExtras(bundle)
        startActivity(intent)
    }


    override fun handlePositiveAlertCallBack() {
        mNoteListViewModel.deleteNote(mNoteModel)
        showToast(getString(R.string.note_deleted))
    }

    inner class DeleteBtnClick : View.OnClickListener{
        override fun onClick(v: View?) {
            mNoteModel = v?.getTag() as NoteModel
            showAlert(getString(R.string.delete_confirmation), R.string.ok, R.string.cancel)
        }
    }

    inner class EditBtnClick : View.OnClickListener{
        override fun onClick(v: View?) {
            mNoteModel = v?.getTag() as NoteModel
            val intent = Intent(this@MainActivity, MakeNoteActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("selectedNote",mNoteModel)
            intent.putExtras(bundle)
            intent.putExtra("editAction",true)
            startActivity(intent)
        }
    }
}
