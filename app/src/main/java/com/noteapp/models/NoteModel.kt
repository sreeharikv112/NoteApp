package com.noteapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteModel(@PrimaryKey(autoGenerate = true)
                     var id:Int?,
                     var noteTitle:String,
                     var noteDescription:String) {

    constructor():this(null,"","")
}

