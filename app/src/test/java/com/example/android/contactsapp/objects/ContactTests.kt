package com.example.android.contactsapp.objects

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ContactTests {

    @Test
    fun contact_is_working_returnsTrue() {
        val contact = Contact("Shivam", "shivam@gmail.com", "some_link")
        assertEquals(contact.name, "Shivam")
        assertEquals(contact.email, "shivam@gmail.com")
        assertEquals(contact.photoURL, "some_link")
    }

    @Test
    fun same_contact_compare_returnsTrue() {
        val contact1  = Contact("Shivam", "shivam@gmail.com", "some_link")
        val contact2  = Contact("Shivam", "shivam@gmail.com", "some_link")
        assertEquals(contact1, contact2)
    }

    @Test
    fun different_contact_compare_returnsFalse() {
        val contact1  = Contact("Shivam", "shivam@gmail.com", "some_link")
        val contact2  = Contact("John", "john@gmail.com", "some_link")
        assertNotEquals(contact1, contact2)
    }
}
