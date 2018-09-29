package com.noteapp.uicomponents.activities.landing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noteapp.R
import com.noteapp.models.NoteModel

class NoteAdapter(noteList:List<NoteModel>,longClickListener: View.OnLongClickListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    var mNoteList:List<NoteModel>
    var mLongClickListener: View.OnLongClickListener

    init {
        this.mNoteList = noteList
        this.mLongClickListener = longClickListener
    }

    fun addNote(noteList:List<NoteModel>){
        this.mNoteList = noteList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return  NoteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val noteModel:NoteModel = mNoteList[position]
        holder.noteTitle.text = noteModel.noteTitle
        holder.noteDesc.text= noteModel.noteDescription
        holder.itemView.tag = noteModel
        holder.itemView.setOnLongClickListener(mLongClickListener)
    }

    override fun getItemCount(): Int {
         return mNoteList.size
    }

    class NoteViewHolder(itemView:View ) : RecyclerView.ViewHolder(itemView){
        var noteTitle: TextView = itemView.findViewById(R.id.noteTitle)
        var noteDesc: TextView = itemView.findViewById(R.id.noteDescription)
    }
}