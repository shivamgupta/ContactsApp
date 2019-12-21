package com.example.android.contactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.contactsapp.adapters.ContactRecyclerViewAdapter
import com.example.android.contactsapp.repository.ContactsRepo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private val TAG = "MainActivity"
    private val contactRecyclerViewAdapter = ContactRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contacts = ContactsRepo(this)
                            .getContacts(80)
        Log.d(TAG, "$contacts")

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = contactRecyclerViewAdapter

        contactRecyclerViewAdapter.loadNewData(contacts)
    }
}
