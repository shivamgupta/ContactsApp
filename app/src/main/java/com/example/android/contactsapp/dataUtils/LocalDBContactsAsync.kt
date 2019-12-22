package com.example.android.contactsapp.dataUtils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.os.AsyncTask
import android.util.Log
import com.example.android.contactsapp.objects.Contact
import java.lang.Exception

class LocalDBContactsAsync(private val listener: OnDBDataAvailable) : AsyncTask<Context, Void, ArrayList<Contact>>() {

    private val TAG : String = "LocalDBContactsAsync"
    @SuppressLint("SdCardPath")
    private val databasePath : String = "/data/user/0/com.example.android.contactsapp/files/contacts.db"
    private lateinit var database : SQLiteDatabase

    interface OnDBDataAvailable {
        fun onDBDataAvailable(contact: ArrayList<Contact>)
        fun onDBError(e: Exception)
    }

    override fun onPreExecute() {
        Log.d(TAG, "onPreExecute starts")
        super.onPreExecute()
        database = openOrCreateDatabase(databasePath,null)
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
        Log.d(TAG, "onPreExecute ends")
    }

    override fun doInBackground(vararg params: Context?): ArrayList<Contact> {
        Log.d(TAG, "doInBackground starts")

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
            Log.e(TAG, "doInBackground error ${e.message}")
            e.printStackTrace()
            cancel(true)
            listener.onDBError(e)
        }

        Log.d(TAG, "doInBackground ends")
        return contacts
    }

    override fun onPostExecute(result: ArrayList<Contact>) {
        Log.d(TAG, "onPostExecute starts")
        //Drop the table
        val dropTableSQL = "DROP TABLE IF EXISTS contacts"
        database.execSQL(dropTableSQL)
        database.close()

        super.onPostExecute(result)
        listener.onDBDataAvailable(result)
        Log.d(TAG, "onPostExecute ends")
    }
}
