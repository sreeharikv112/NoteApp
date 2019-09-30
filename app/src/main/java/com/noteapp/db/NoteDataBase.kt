package com.noteapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noteapp.models.NoteModel
import com.noteapp.models.SecurityQuestionModel

@Database(entities = [NoteModel::class, SecurityQuestionModel::class], version = 1, exportSchema = false)
@TypeConverters(TimeStampConverter::class)
abstract class NoteDataBase : RoomDatabase(){

    abstract fun noteItemAndNotesModel(): NoteModelDao
    companion object {
        private var INSTANCE : NoteDataBase? =null
        fun getInstance(context : Context):NoteDataBase?{
            if(INSTANCE == null){
                synchronized(NoteDataBase::class){
                    INSTANCE=Room.databaseBuilder(context.applicationContext,
                            NoteDataBase::class.java, "notedb.db").
                            fallbackToDestructiveMigration().
                            build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance(){
            INSTANCE =null
        }
    }
}