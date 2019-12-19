package com.example.android.contactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android.contactsapp.asyncTasks.DownloadData
import com.example.android.contactsapp.dataUtils.ContactJSONUtils
import com.example.android.contactsapp.objects.Contact
import java.lang.Exception

class MainActivity : AppCompatActivity(), DownloadData.OnDownloadComplete, ContactJSONUtils.OnDataAvailable {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getRawData = DownloadData(this)
        getRawData.execute("https://randomuser.me/api/?results=10")
    }

    override fun onDownloadComplete(data: String) {
        Log.d(TAG, "onDownloadComplete called")

        val getContactJsonData = ContactJSONUtils(this)
        getContactJsonData.execute(data)
    }

    override fun onDataAvailable(contact: ArrayList<Contact>) {
        Log.d(TAG, "onDataAvailable called, data is $contact")
    }

    override fun onError(e: Exception) {
        Log.e(TAG, "onError called with ${e.message}")
    }
}
