package com.noteapp.db

import com.noteapp.common.AppLogger
import com.noteapp.models.NoteModel
import org.jetbrains.anko.doAsync


class DBUtils {

    val TAG: String = DBUtils::class.java.simpleName
    var mAppLogger : AppLogger = AppLogger()

    fun deleteNoteItem(noteDataBase:NoteDataBase,noteModel: NoteModel){
        mAppLogger.debug(TAG,"deleteNoteItem")
        doAsync {
            noteDataBase.noteItemAndNotesModel().deleteNote(noteModel)
            mAppLogger.debug(TAG,"noteModel deleted")
        }
    }

    fun addNote(noteDataBase:NoteDataBase,noteModel: NoteModel){
        mAppLogger.debug(TAG,"addNote")
        doAsync {
            noteDataBase.noteItemAndNotesModel().insertNote(noteModel)
            mAppLogger.debug(TAG,"noteModel added")
        }
    }

    fun updateNote(noteDataBase:NoteDataBase,noteModel: NoteModel){
        mAppLogger.debug(TAG,"update Note")
        doAsync {
            noteDataBase.noteItemAndNotesModel().updateNote(noteModel)
            mAppLogger.debug(TAG,"noteModel updated")
        }

    }
}