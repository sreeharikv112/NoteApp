package com.noteapp.models

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.io.Serializable

@Entity 
data class SecurityQuestionModel(@PrimaryKey
                                 var id:Int?,
                                 var key:Int,
                                 var question:String,
                                 var answer:String):Serializable