package com.example.buoi5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView

class ContactAdapter (private val contacts: MutableList<Contact> = mutableListOf(), private val onItemClick: (Contact) -> Unit, private val onDeleteClick: (Int) -> Unit) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): ContactViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        val item = contacts[position]

        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
        holder.btnDelete.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        val imgAvatar: ShapeableImageView = itemView.findViewById(R.id.img_avatar)
        val btnDelete: MaterialButton = itemView.findViewById(R.id.btn_delete)

        fun bind(contact: Contact) {
            tvName.text = contact.name
            tvPhone.text = contact.phone
            imgAvatar.setImageResource(contact.avatar)
        }
    }
}