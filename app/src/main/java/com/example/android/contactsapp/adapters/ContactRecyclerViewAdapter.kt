package com.example.android.contactsapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.android.contactsapp.ContactActivity
import com.example.android.contactsapp.R
import com.example.android.contactsapp.objects.Contact
import com.squareup.picasso.Picasso
import kotlin.coroutines.coroutineContext

class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val photo : ImageView = view.findViewById(R.id.photo)
    val name : TextView = view.findViewById(R.id.name)
}

class ContactRecyclerViewAdapter(private var contacts : ArrayList<Contact>) : RecyclerView.Adapter<ContactViewHolder>() {

    private val TAG = "ContRecyViewAdap"
    private lateinit var context: Context

    fun loadNewData(newData: ArrayList<Contact>) {
        contacts = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        Log.d(TAG, "onCreateViewHolder called")
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount called")
        return if(contacts.isEmpty()) 0 else contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder called")

        val contact = contacts[position]

        Picasso.with(holder.photo.context)
            .load(contact.photoURL)
            .error(R.drawable.placeholder)
            .placeholder(R.drawable.placeholder)
            .into(holder.photo)

        holder.name.text = contact.name

        holder.itemView.setOnClickListener {
            //Toast.makeText(context, holder.name.text, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ContactActivity::class.java)
            intent.putExtra("obj", contact)
            startActivity(context, intent, null)
        }
    }
}
