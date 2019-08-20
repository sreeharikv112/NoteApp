package com.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.noteapp.models.NoteModel
import com.noteapp.models.SecurityQuestionModel

@Dao
interface NoteModelDao {

    @Query("SELECT * FROM NoteModel ORDER BY dateSaved DESC")
    fun getAllNotes(): LiveData<List<NoteModel>>

    @Query("SELECT * FROM NoteModel WHERE id = :id")
    fun getNoteById(id: String ):NoteModel

    @Insert
    fun insertNote(noteModel: NoteModel)

    @Delete
    fun deleteNote(noteModel: NoteModel)

    @Update
    fun updateNote(noteModel: NoteModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateSecurityQuestion(securityQuestionModel: SecurityQuestionModel)

    @Query("SELECT * FROM SecurityQuestionModel")
    fun getSecurityQuestion(): SecurityQuestionModel

}



