package com.noteapp.db

import com.noteapp.common.AppLogger
import com.noteapp.models.NoteModel
import com.noteapp.models.SecurityQuestionModel
import com.noteapp.uicomponents.activities.setuppin.IGetSecurityQuestionListener
import com.noteapp.uicomponents.activities.setuppin.IUpdateSecurityListener
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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

    fun fetchSecurityQuestion(noteDataBase:NoteDataBase,listenerIGet: IGetSecurityQuestionListener){
        var savedSecurityQstn : SecurityQuestionModel? = null
        Completable.fromAction {
            savedSecurityQstn = noteDataBase.noteItemAndNotesModel().getSecurityQuestion()
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {

                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        mAppLogger.debug(TAG,"fetchSecurityQuestion SUCCESS" )
                        listenerIGet.fetchSecurityQstnListener(savedSecurityQstn)
                    }

                    override fun onError(e: Throwable) {
                        mAppLogger.debug(TAG,"fetchSecurityQuestion ERROR" )
                        listenerIGet.fetchSecurityQstnListener(null)
                    }
                })
    }

    fun setOrUpdateSecurityQstn(noteDataBase:NoteDataBase,securityQuestionModel: SecurityQuestionModel,
                                listener: IUpdateSecurityListener){
        Completable.fromAction{
            mAppLogger.debug(TAG,"updateSecurityQstn")
            noteDataBase.noteItemAndNotesModel().insertOrUpdateSecurityQuestion(securityQuestionModel)

        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(object : CompletableObserver {

                    override fun onSubscribe(d: Disposable) {}

                    override fun onComplete() {
                        mAppLogger.debug(TAG,"setOrUpdateSecurityQstn SUCCESS" )
                        listener.didSecurityQuestionUpdated(true)
                    }

                    override fun onError(e: Throwable) {
                        mAppLogger.debug(TAG,"setOrUpdateSecurityQstn ERROR" )
                        listener.didSecurityQuestionUpdated(false)
                    }
                })
    }
}