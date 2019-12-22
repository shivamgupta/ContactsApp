package com.example.android.contactsapp.repository

import android.util.Log
import com.example.android.contactsapp.dataUtils.ContactJSONUtils
import com.example.android.contactsapp.dataUtils.DownloadData
import com.example.android.contactsapp.dataUtils.LocalDBContactsAsync
import com.example.android.contactsapp.objects.Contact
import java.lang.Exception

class ContactsRepo() :
    DownloadData.OnDownloadComplete, ContactJSONUtils.OnDataAvailable, LocalDBContactsAsync.OnDBDataAvailable {

    private val TAG = "ContactsRepo"

    private val contacts = ArrayList<Contact>()

    fun getContacts(contactsType: String) : ArrayList<Contact>  {

        val rawData = DownloadData(this)
                            .execute("https://randomuser.me/api/?results=100")
                            .get()

        if (contactsType.equals("both")) {
            val contactsFromAPI = ContactJSONUtils(this).execute(rawData).get()
            val contactsFromDB = LocalDBContactsAsync(this).execute().get()
            contacts.addAll(contactsFromDB)
            contacts.addAll(contactsFromAPI)
        }
        else if (contactsType.equals("cloud")) {
            val contactsFromAPI = ContactJSONUtils(this).execute(rawData).get()
            contacts.addAll(contactsFromAPI)
        }
        else if (contactsType.equals("local")) {
            val contactsFromDB = LocalDBContactsAsync(this).execute().get()
            contacts.addAll(contactsFromDB)
        }

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
    }

    override fun onDBError(e: Exception) {
        Log.e(TAG, "onDBError called with ${e.message}")
    }
}
