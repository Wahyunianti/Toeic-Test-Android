package com.example.projectuts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

class KategoriListAdapter(
    val kategori: ArrayList<KategoriItem.Data>
): RecyclerView.Adapter<KategoriListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.listening_adapter, parent, false)
    )
    override fun getItemCount() = kategori.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val  txt2 = view.findViewById<TextView>(R.id.lista)
    }
    override fun onBindViewHolder(holder: KategoriListAdapter.ViewHolder, position: Int) {
        val q = kategori[position]
        holder.txt2.text = q.nama_kategori
    }

    public fun setData(data: List<KategoriItem.Data>){
        kategori.clear()
        kategori.addAll(data)
        notifyDataSetChanged()
    }


}