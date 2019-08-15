package com.noteapp.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.noteapp.db.DBUtils
import com.noteapp.db.NoteDataBase
import org.jetbrains.anko.doAsync

class SecurityQuestionViewModel(application: Application):  AndroidViewModel(application) {

    private var mNoteDataBase: NoteDataBase = NoteDataBase.getInstance(getApplication())!!

    var savedSecurityQstn : SecurityQuestionModel? = null
    val mTag = SecurityQuestionViewModel::class.java.simpleName

    /*init {
        savedSecurityQstn = mNoteDataBase.noteItemAndNotesModel().getSecurityQuestion()
    }*/
    fun insertOrUpdateSecurityQuestion(securityQuestionModel: SecurityQuestionModel){
        var dbUtils= DBUtils()
        dbUtils.setOrUpdateSecurityQstn(mNoteDataBase,securityQuestionModel)
    }

    fun getSecurityQuestion(): SecurityQuestionModel {
        Log.d(mTag,"getSecurityQuestion called")
        doAsync {
        savedSecurityQstn = mNoteDataBase.noteItemAndNotesModel().getSecurityQuestion()
            Log.d(mTag,"savedSecurityQstn  is ${savedSecurityQstn}")
        }
        //Log.d(mTag,"returning savedSecurityQstn is ${savedSecurityQstn}")
        return savedSecurityQstn!!
    }
}