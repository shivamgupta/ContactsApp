package com.example.android.contactsapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.android.contactsapp.R
import com.example.android.contactsapp.objects.Contact
import kotlinx.android.synthetic.main.fragment_contact.*

class ContactFragment(private var contact: Contact) : Fragment() {

    private val TAG = "ContactFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView")
        val root =  inflater.inflate(R.layout.fragment_contact, container,false)
        val name = root.findViewById<TextView>(R.id.fragment_contact_name) as TextView
        name.text = contact.name
        return root
    }
}
