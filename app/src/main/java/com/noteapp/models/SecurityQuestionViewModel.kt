package com.noteapp.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.noteapp.db.DBUtils
import com.noteapp.db.NoteDataBase
import com.noteapp.uicomponents.activities.setuppin.IGetSecurityQuestionListener
import com.noteapp.uicomponents.activities.setuppin.IUpdateSecurityListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SecurityQuestionViewModel(application: Application):  AndroidViewModel(application) {

    private var mNoteDataBase: NoteDataBase = NoteDataBase.getInstance(getApplication())!!

    var dbUtils= DBUtils()

    fun insertOrUpdateSecurityQuestion(securityQuestionModel: SecurityQuestionModel,listener: IUpdateSecurityListener){

        dbUtils.setOrUpdateSecurityQstn(mNoteDataBase,securityQuestionModel,listener)

    }

    fun getSecurityQuestion(listenerIGet: IGetSecurityQuestionListener){

        dbUtils.fetchSecurityQuestion(mNoteDataBase,listenerIGet)

    }
}