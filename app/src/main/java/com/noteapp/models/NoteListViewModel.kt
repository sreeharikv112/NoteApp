package com.noteapp.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.noteapp.db.DBUtils
import com.noteapp.db.NoteDataBase

class NoteListViewModel : AndroidViewModel {

    var mNoteList: LiveData<List<NoteModel>>
    var mNoteDataBase: NoteDataBase = NoteDataBase.getInstance(getApplication())!!

    constructor(application: Application):super(application){
        mNoteList = mNoteDataBase.noteItemAndNotesModel().getAllNotes()
    }

    fun deleteNote(noteModel: NoteModel){
        val dbUtils = DBUtils()
        dbUtils.deleteNoteItem(mNoteDataBase,noteModel)
    }
}