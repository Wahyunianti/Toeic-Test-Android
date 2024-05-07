package com.example.projectuts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

class ReadingListAdapter(
    val kategori: ArrayList<ReadingItem.Data>,
    val listener: OnAdapterListener
): RecyclerView.Adapter<ReadingListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.reading_adapter, parent, false)
    )
    override fun getItemCount() = kategori.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val  txt2 = view.findViewById<TextView>(R.id.read)
    }

    override fun onBindViewHolder(holder: ReadingListAdapter.ViewHolder, position: Int) {
        val q = kategori[position]
        holder.txt2.text = q.nama_kategori
        holder.itemView.setOnClickListener {
            listener.onClick(q)
        }
    }

    public fun setData(data: List<ReadingItem.Data>){
        kategori.clear()
        kategori.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(note : ReadingItem.Data)
    }


}