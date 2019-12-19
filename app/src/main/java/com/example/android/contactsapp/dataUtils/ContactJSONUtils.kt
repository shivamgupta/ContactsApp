package com.example.android.contactsapp.dataUtils

import android.os.AsyncTask
import android.util.Log
import com.example.android.contactsapp.objects.Contact
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class ContactJSONUtils(private val listener: OnDataAvailable) : AsyncTask<String, Void, ArrayList<Contact>>() {

    private val TAG : String = "ContactJSONUtils"

    interface OnDataAvailable {
        fun onDataAvailable(contact: ArrayList<Contact>)
        fun onError(e: Exception)
    }

    override fun doInBackground(vararg params: String?): ArrayList<Contact> {
        Log.d(TAG, "doInBackground starts")

        val contacts = ArrayList<Contact>()

        try {
            val jsonData = JSONObject(params[0]!!)
            val resultsArray = jsonData.getJSONArray("results")

            for(i in 0 until resultsArray.length()) {
                val contactInfo = resultsArray.getJSONObject(i)

                val name : String =
                    contactInfo.getJSONObject("name").getString("first") +
                            " " +
                            contactInfo.getJSONObject("name").getString("last")
                val email: String = contactInfo.getString("email")
                val photoURL: String = contactInfo.getJSONObject("picture").getString("medium")

                val contactObject = Contact(name, email, photoURL)
                contacts.add(contactObject)
                Log.d(TAG, "doInBackground ${contactObject}")
            }
        } catch (e: JSONException) {
            Log.e(TAG, "doInBackground error ${e.message}")
            e.printStackTrace()
            cancel(true)
            listener.onError(e)
        }
        Log.d(TAG, "doInBackground ends")
        return contacts
    }

    override fun onPostExecute(result: ArrayList<Contact>) {
        Log.d(TAG, "onPostExecute starts")
        super.onPostExecute(result)
        listener.onDataAvailable(result)
        Log.d(TAG, "onPostExecute ends")
    }
}
