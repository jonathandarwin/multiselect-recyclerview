package com.jonathandarwin.multiselectrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonathandarwin.multiselectrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val contactList = ArrayList<Contact>()
    private lateinit var contactAdapter : ContactAdapter
    private lateinit var binding : ActivityMainBinding
    private var counter = 5
    private var onDelete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        getContactList()
        setAdapter()
        setListener()
    }

    private fun getContactList(){
        for(i in 0..counter) {
            contactList.add(Contact(i, "Contact $i", false, View.INVISIBLE))
        }
    }

    private fun setAdapter(){
        contactAdapter = ContactAdapter(contactList)
        contactAdapter.setOnLongClickListener {
            if(!onDelete){
                binding.llDelete.visibility = View.VISIBLE
                it.isChecked = true
                setCheckboxVisibility(true)
                onDelete = true
            }
        }
        binding.rvContact.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = contactAdapter
        }
    }

    private fun setCheckboxVisibility(isVisibile : Boolean){
        contactList.forEach {
            it.visibility = if(isVisibile) View.VISIBLE else View.INVISIBLE
        }
        contactAdapter.notifyDataSetChanged()
    }

    private fun removeChecked(){
        contactList.forEach { it.isChecked = false }
    }

    private fun delete(){
        contactList.removeAll { it.isChecked }
        contactAdapter.notifyDataSetChanged()
    }

    private fun setListener(){
        binding.apply {
            tvAddContact.setOnClickListener(this@MainActivity)
            tvCancel.setOnClickListener(this@MainActivity)
            tvDelete.setOnClickListener(this@MainActivity)
        }
    }

    private fun closeOnDelete(){
        setCheckboxVisibility(false)
        removeChecked()
        binding.llDelete.visibility = View.GONE
        onDelete = false
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.tvAddContact.id -> {
                contactList.add(Contact(++counter, "Contact $counter", false, if(onDelete) View.VISIBLE else View.INVISIBLE))
                contactAdapter.notifyItemInserted(contactList.size-1)
            }
            binding.tvCancel.id -> {
                closeOnDelete()
            }
            binding.tvDelete.id -> {
                delete()
                closeOnDelete()
            }
        }
    }
}
