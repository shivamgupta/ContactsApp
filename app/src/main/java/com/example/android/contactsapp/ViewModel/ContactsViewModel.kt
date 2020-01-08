package com.example.android.contactsapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.contactsapp.objects.Contact
import com.example.android.contactsapp.repository.ContactsRepo

class ContactsViewModel : ViewModel() {
    private val contactsList = MutableLiveData<ArrayList<Contact>>()
    val contactsLiveData: LiveData<ArrayList<Contact>> = contactsList
    init {
        contactsList.postValue(ArrayList(0))
    }

    fun getContactsList(contactSource: String) {
        contactsList.postValue(ContactsRepo().getContacts(contactSource))
    }
}
