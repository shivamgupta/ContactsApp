package com.example.android.contactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android.contactsapp.fragments.ContactFragment
import com.example.android.contactsapp.objects.Contact

class ContactActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val contact: Contact = intent!!.getParcelableExtra("obj")
        //Toast.makeText(this, contact.photoURL, Toast.LENGTH_SHORT).show()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_holder, ContactFragment(contact))
            .disallowAddToBackStack()
            .commit()
    }

}
