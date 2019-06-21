package com.noteapp.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteModel(@PrimaryKey(autoGenerate = true)
                     var id:Int?,
                     var noteTitle:String,
                     var noteDescription:String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString()) {
    }

    constructor():this(null,"","")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(noteTitle)
        parcel.writeString(noteDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteModel> {
        override fun createFromParcel(parcel: Parcel): NoteModel {
            return NoteModel(parcel)
        }

        override fun newArray(size: Int): Array<NoteModel?> {
            return arrayOfNulls(size)
        }
    }
}

