package com.example.android.contactsapp.repository

import android.content.Context
import android.util.Log
import com.example.android.contactsapp.dataUtils.ContactJSONUtils
import com.example.android.contactsapp.dataUtils.DownloadData
import com.example.android.contactsapp.dataUtils.LocalDBContacts
import com.example.android.contactsapp.dataUtils.LocalDBContactsAsync
import com.example.android.contactsapp.objects.Contact
import java.lang.Exception

class ContactsRepo(val context: Context) :
    DownloadData.OnDownloadComplete, ContactJSONUtils.OnDataAvailable, LocalDBContactsAsync.OnDBDataAvailable {

    private val TAG = "ContactsRepo"

    private val contacts = ArrayList<Contact>()

    fun getContacts(numberOfContactsRequested: Int) : ArrayList<Contact>  {

        val rawData = DownloadData(this)
                            .execute("https://randomuser.me/api/?results="
                                    + numberOfContactsRequested.toString()
                            ).get()

        val contactsFromAPI = ContactJSONUtils(this).execute(rawData).get()
        val contactsFromDB = LocalDBContacts(context).getContacts()

        contacts.addAll(contactsFromDB)
        contacts.addAll(contactsFromAPI)

        Log.d(TAG, "Contacts Data is now available - ${contacts}")
        return contacts
    }

    override fun onDownloadComplete(data: String) {
        Log.d(TAG, "onDownloadComplete called")
    }

    override fun onDataAvailable(contact: ArrayList<Contact>) {
        Log.d(TAG, "onDataAvailable called")
    }

    override fun onError(e: Exception) {
        Log.e(TAG, "onError called with ${e.message}")
    }

    override fun onDBDataAvailable(contact: ArrayList<Contact>) {
        Log.d(TAG, "onDBDataAvailable called")
        contacts.addAll(contact)
    }

    override fun onDBError(e: Exception) {
        Log.e(TAG, "onDBError called with ${e.message}")
    }
}
