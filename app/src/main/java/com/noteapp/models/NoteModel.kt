package com.noteapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class NoteModel(@PrimaryKey(autoGenerate = true)
                     var id:Int?,
                     var noteTitle:String,
                     var noteDescription:String,
                     var dateSaved: Date?,
                     var noteColor: Int
                     ) : Serializable
