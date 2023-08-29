package com.jakaa.noteappsqlite

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, context: Context): RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val db: NoteDatabaseHelper = NoteDatabaseHelper(context)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleTextView = itemView.findViewById<TextView>(R.id.titleEditText)
        val contentTextView = itemView.findViewById<TextView>(R.id.contentEditText)
        val updateButton = itemView.findViewById<ImageView>(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener {
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }

    }

    fun refreshData(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }
}





































































