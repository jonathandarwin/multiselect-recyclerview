package com.jonathandarwin.multiselectrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jonathandarwin.multiselectrecyclerview.databinding.ListContactItemBinding

class ContactAdapter(private val contactList : ArrayList<Contact>) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private lateinit var callback : (contact : Contact) -> Unit

    inner class ContactViewHolder(private val binding : ListContactItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(contact : Contact){
            binding.contact = contact

            binding.root.setOnLongClickListener {
                callback(contact)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(ListContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = contactList.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) = holder.bind(contactList[position])

    fun setOnLongClickListener(callback : (contact : Contact) -> Unit) {
        this.callback = callback
    }
}