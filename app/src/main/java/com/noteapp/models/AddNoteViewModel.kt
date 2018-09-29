package com.noteapp.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.noteapp.db.DBUtils
import com.noteapp.db.NoteDataBase

class AddNoteViewModel : AndroidViewModel {

    var mNoteDataBase: NoteDataBase = NoteDataBase.getInstance(getApplication())!!

    constructor(application: Application):super(application){    }

    fun addNote(noteModel: NoteModel ){
        var dbUtils= DBUtils()
        dbUtils.addNote(mNoteDataBase,noteModel)
    }
}