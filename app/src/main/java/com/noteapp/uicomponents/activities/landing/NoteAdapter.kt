package com.noteapp.uicomponents.activities.landing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noteapp.R
import com.noteapp.models.NoteModel

class NoteAdapter(var context: Context,
                  noteList:List<NoteModel>,
                  onClickListener: View.OnClickListener ,
                  onDeletePressed: View.OnClickListener ,
                  onEditPressed :  View.OnClickListener
                  ) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var mNoteList:List<NoteModel>
    private var mClickListener : View.OnClickListener
    private var mDeleteClickListener : View.OnClickListener
    private var mEditClickListener : View.OnClickListener

    init {
        this.mNoteList = noteList
        this.mClickListener = onClickListener
        this.mDeleteClickListener = onDeletePressed
        this.mEditClickListener = onEditPressed
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
        holder.deleteNote.tag = noteModel
        holder.editNote.tag = noteModel
        holder.itemView.setOnClickListener(mClickListener)
        holder.deleteNote.setOnClickListener(mDeleteClickListener)
        holder.editNote.setOnClickListener(mEditClickListener)

        holder.noteTitle.setTextColor(context.resources.getColor(android.R.color.white))
        holder.noteDesc.setTextColor(context.resources.getColor(android.R.color.white))

        when(noteModel.noteColor){
            1 ->
                holder.relativeBack.setBackgroundColor(context.resources.getColor(android.R.color.holo_red_light))
            2 ->
                holder.relativeBack.setBackgroundColor(context.resources.getColor(android.R.color.holo_green_light))
            3 ->
                holder.relativeBack.setBackgroundColor(context.resources.getColor(android.R.color.holo_blue_light))
            4 ->
                holder.relativeBack.setBackgroundColor(context.resources.getColor(android.R.color.holo_orange_light))

            else -> {
                holder.noteTitle.setTextColor(context.resources.getColor(android.R.color.darker_gray))
                holder.noteDesc.setTextColor(context.resources.getColor(android.R.color.darker_gray))
                holder.relativeBack.setBackgroundColor(context.resources.getColor(android.R.color.white))
            }
        }

    }

    override fun getItemCount(): Int {
         return mNoteList.size
    }

    class NoteViewHolder(itemView:View ) : RecyclerView.ViewHolder(itemView){
        var relativeBack: RelativeLayout = itemView.findViewById(R.id.relativeBackground)
        var noteTitle: TextView = itemView.findViewById(R.id.noteTitle)
        var noteDesc: TextView = itemView.findViewById(R.id.noteDescription)
        var deleteNote : ImageView = itemView.findViewById(R.id.deleteBtn)
        var editNote : ImageView = itemView.findViewById(R.id.editBtn)
    }
}