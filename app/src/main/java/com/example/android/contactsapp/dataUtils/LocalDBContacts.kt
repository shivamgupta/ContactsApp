package com.example.android.contactsapp.dataUtils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.android.contactsapp.objects.Contact
import java.lang.Exception

class LocalDBContacts(context: Context) {

    private val TAG : String = "LocalDBContacts"

    private var database : SQLiteDatabase = context.openOrCreateDatabase("contacts.db", Context.MODE_PRIVATE, null)

    fun getContacts(): ArrayList<Contact> {
        Log.d(TAG, "getContacts starts")
        prePopulateDB()

        val contacts = ArrayList<Contact>()

        try {
            val selectSQL = database.rawQuery("SELECT * FROM contacts", null)
            selectSQL.use {
                while (it.moveToNext()) {
                    //Cycle through all the contacts
                    with(it) {
                        //val id = getLong(0)
                        val name = getString(1)
                        val email = getString(2)
                        val photoURL = getString(3)
                        contacts.add(Contact(name, email, photoURL))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "getContacts error ${e.message}")
            e.printStackTrace()
        }

        Log.d(TAG, "getContacts ends")
        dropTable()
        return contacts
    }

    private fun prePopulateDB() {
        Log.d(TAG, "prePopulateDB starts")
        val createTableSQL = "CREATE TABLE IF NOT EXISTS contacts(_id INTEGER PRIMARY KEY NOT NULL, name TEXT, email TEXT, photoURL TEXT)"
        database.execSQL(createTableSQL)

        val insertRecordSQL = "INSERT INTO contacts(name, email, photoURL) VALUES ('Shivam Gupta', 'shivam@gmail.com', 'https://media.licdn.com/dms/image/C5603AQFT96iebh4BWw/profile-displayphoto-shrink_200_200/0?e=1582156800&v=beta&t=QzYuyG524YaTNQjoP6qNX3r0OU6ozcq5SOurHT3Qt7g')"
        database.execSQL(insertRecordSQL)

        val contact = ContentValues().apply {
            put("name", "John Smith")
            put("email", "john@gmail.com")
            put("photoURL", "https://randomuser.me/api/portraits/men/3.jpg")
        }
        database.insert("contacts", null, contact)
        Log.d(TAG, "prePopulateDB ends")
    }

    private fun dropTable() {
        Log.d(TAG, "dropTable starts")
        //Drop the table
        val dropTableSQL = "DROP TABLE IF EXISTS contacts"
        database.execSQL(dropTableSQL)
        database.close()
        Log.d(TAG, "dropTable ends")
    }
}
