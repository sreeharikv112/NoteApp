package com.noteapp.db

import android.util.Log
import com.noteapp.common.AppLogger
import com.noteapp.models.NoteModel
import org.jetbrains.anko.doAsync


class DBUtils {

    val TAG: String = DBUtils::class.java.simpleName
    var mAppLogger : AppLogger = AppLogger()

    fun deleteNoteItem(noteDataBase:NoteDataBase,noteModel: NoteModel){
        mAppLogger.d(TAG,"deleteNoteItem")
        doAsync {
            noteDataBase.noteItemAndNotesModel().deleteNote(noteModel)
            Log.d(TAG,"noteModel deleted")
        }
    }

    fun addNote(noteDataBase:NoteDataBase,noteModel: NoteModel){
        mAppLogger.d(TAG,"addNote")
        doAsync {
            noteDataBase.noteItemAndNotesModel().insertNote(noteModel)
            Log.d(TAG,"noteModel added")
        }

    }
}