package com.example.projectuts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

class UserListAdapter(
    val user: ArrayList<UserItem.Data>,
): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.activity_editprofil, parent, false)
    )
    override fun getItemCount() = user.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val  nm = view.findViewById<EditText>(R.id.namamu)
        val  un = view.findViewById<EditText>(R.id.usrmu)
        val  pw = view.findViewById<EditText>(R.id.pwmu)
        val  st = view.findViewById<TextView>(R.id.sttmu)
    }
    override fun onBindViewHolder(holder: UserListAdapter.ViewHolder, position: Int) {
        val q = user[position]
        holder.nm.setText(q.nama)
        holder.un.setText(q.username)
        holder.pw.setText(q.password)
        holder.st.setText(q.status)
    }

    public fun setData(data: List<UserItem.Data>){
        user.clear()
        user.addAll(data)
        notifyDataSetChanged()
    }

}