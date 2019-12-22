package com.example.android.contactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.contactsapp.ViewModel.ContactsViewModel
import com.example.android.contactsapp.adapters.ContactRecyclerViewAdapter
import com.example.android.contactsapp.objects.Contact
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private val TAG = "MainActivity"

    private val contactRecyclerViewAdapter = ContactRecyclerViewAdapter(ArrayList())
    private lateinit var contactSource : String

    private val contactsViewModel: ContactsViewModel by lazy { ViewModelProviders.of(this).get(ContactsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactSource = getString(R.string.contact_source_both)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = contactRecyclerViewAdapter

        contactsViewModel.contacts.observe(this, Observer<ArrayList<Contact>> { contactRecyclerViewAdapter.loadNewData(it) })
        contactsViewModel.getContactsList(contactSource)
    }

    /*
    * ------------------------------------------------
    * OPTIONS MENU FUNCTION
    * ------------------------------------------------
    * */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.contact_menu, menu)

        if (contactSource.equals(getString(R.string.contact_source_both))) {
            menu.findItem(R.id.allContacts)?.isChecked = true
        } else if (contactSource.equals(getString(R.string.contact_source_api))){
            menu.findItem(R.id.apiContacts)?.isChecked = true
        } else {
            menu.findItem(R.id.localContacts)?.isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.allContacts ->
                contactSource = getString(R.string.contact_source_both)
            R.id.apiContacts ->
                contactSource = getString(R.string.contact_source_api)
            R.id.localContacts ->
                contactSource = getString(R.string.contact_source_local)
            else ->
                return super.onOptionsItemSelected(item)
        }
        item.isChecked = true
        contactsViewModel.getContactsList(contactSource)
        return true
    }
}
