package com.example.android.contactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.contactsapp.adapters.ContactRecyclerViewAdapter
import com.example.android.contactsapp.repository.ContactsRepo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private val TAG = "MainActivity"
    private val contactRecyclerViewAdapter = ContactRecyclerViewAdapter(ArrayList())
    private var contactSource = "both"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contacts = ContactsRepo(this).getContacts(contactSource)
        Log.d(TAG, "$contacts")

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = contactRecyclerViewAdapter

        contactRecyclerViewAdapter.loadNewData(contacts)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contact_menu, menu)

        if (contactSource.equals("both")) {
            menu?.findItem(R.id.allContacts)?.isChecked = true
        } else if (contactSource.equals("cloud")){
            menu?.findItem(R.id.apiContacts)?.isChecked = true
        } else {
            menu?.findItem(R.id.localContacts)?.isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.allContacts ->
                contactSource = "both"
            R.id.apiContacts ->
                contactSource = "cloud"
            R.id.localContacts ->
                contactSource = "local"
            else ->
                return super.onOptionsItemSelected(item)
        }
        item.isChecked = true
        val contacts = ContactsRepo(this).getContacts(contactSource)
        contactRecyclerViewAdapter.loadNewData(contacts)
        return true
    }
}
