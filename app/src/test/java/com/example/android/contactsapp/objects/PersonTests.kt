package com.example.android.contactsapp.objects

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PersonTests {

    @Test
    fun person_is_working_returnsTrue() {
        val person : Person = Person("Shivam", "shivam@gmail.com", "some_link")
        assertEquals(person.name, "Shivam")
        assertEquals(person.email, "shivam@gmail.com")
        assertEquals(person.photoURL, "some_link")
    }

    @Test
    fun same_person_compare_returnsTrue() {
        val person1 : Person = Person("Shivam", "shivam@gmail.com", "some_link")
        val person2 : Person = Person("Shivam", "shivam@gmail.com", "some_link")
        assertEquals(person1, person2)
    }

    @Test
    fun different_person_compare_returnsFalse() {
        val person1 : Person = Person("Shivam", "shivam@gmail.com", "some_link")
        val person2 : Person = Person("John", "john@gmail.com", "some_link")
        assertNotEquals(person1, person2)
    }
}
