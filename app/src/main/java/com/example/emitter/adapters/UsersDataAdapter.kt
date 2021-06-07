package com.example.emitter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emitter.R
import com.example.emitter.pojo.UsersDataModelItem

class UsersDataAdapter(
    private val usersList: List<UsersDataModelItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<UsersDataAdapter.UsersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rec, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

        holder.name.text = usersList[position].name
        holder.email.text = usersList[position].email
        holder.id.text = usersList[position].id.toString()
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

        val name: TextView = itemView.findViewById(R.id.tv_name_2)
        val id: TextView = itemView.findViewById(R.id.tv_id_2)
        val email: TextView = itemView.findViewById(R.id.tv_email_2)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}