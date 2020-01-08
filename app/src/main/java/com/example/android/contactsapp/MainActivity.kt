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

    private val contactRecyclerViewAdapter = ContactRecyclerViewAdapter(ArrayList())
    private var contactSource : String = ""
    private var bundle: Bundle = Bundle()

    private val contactsViewModel: ContactsViewModel by lazy { ViewModelProviders.of(this).get(ContactsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = contactRecyclerViewAdapter

        if(savedInstanceState != null) {
            bundle = savedInstanceState
            contactSource = savedInstanceState.getString("contactSource")!!
            contactsViewModel.getContactsList(contactSource)
        } else {
            contactSource = getString(R.string.contact_source_both)
            contactsViewModel.getContactsList(contactSource)
        }
        contactsViewModel.contactsLiveData.observe(this,
            Observer<ArrayList<Contact>> {
                    newData -> contactRecyclerViewAdapter.loadNewData(newData)
            })
    }

    /*
    * ------------------------------------------------
    * OPTIONS MENU FUNCTION
    * ------------------------------------------------
    * */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.contact_menu, menu)
        when (bundle.getString("contactSource")) {
            getString(R.string.contact_source_both) ->
                menu.findItem(R.id.allContacts).isChecked = true
            getString(R.string.contact_source_api) ->
                menu.findItem(R.id.apiContacts).isChecked = true
            getString(R.string.contact_source_local) ->
                menu.findItem(R.id.localContacts).isChecked = true
            else ->
                menu.findItem(R.id.allContacts).isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        contactSource = when (item.itemId) {
            R.id.allContacts ->
                getString(R.string.contact_source_both)
            R.id.apiContacts ->
                getString(R.string.contact_source_api)
            R.id.localContacts ->
                getString(R.string.contact_source_local)
            else ->
                return super.onOptionsItemSelected(item)
        }
        item.isChecked = true
        contactsViewModel.getContactsList(contactSource)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("contactSource", contactSource)
    }
}
