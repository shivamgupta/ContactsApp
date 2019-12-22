package com.example.android.contactsapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.contactsapp.objects.Contact
import com.example.android.contactsapp.repository.ContactsRepo
import java.util.*
import kotlin.coroutines.coroutineContext

class ContactsViewModel : ViewModel() {

    private val TAG = "ContactsViewModel"

    private val contactsList = MutableLiveData<List<Contact>>()

    val contacts: LiveData<List<Contact>> = contactsList

    init {
        contactsList.postValue(Collections.emptyList<Contact>())
    }

    fun getContactsList(contactSource: String) {
        Log.d(TAG, "getContactsList called")
        //contactsList.postValue(ContactsRepo(this).getContacts(contactSource))
    }
}