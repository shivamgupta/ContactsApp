package com.example.android.contactsapp

import android.util.Log
import com.example.android.contactsapp.dataUtils.DownloadData
import com.example.android.contactsapp.objects.Contact
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTests {

    @Test
    fun contact_isCorrect() {
        val contact : Contact = Contact("Shivam", "shivam@gmail.com", "some_link")
        assertEquals(contact.name, "Shivam")
        assertEquals(contact.email, "shivam@gmail.com")
        assertEquals(contact.photoURL, "some_link")
    }
}
