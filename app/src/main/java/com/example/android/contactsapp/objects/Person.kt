package com.example.android.contactsapp.objects

import android.util.Log
import java.io.IOException
import java.io.ObjectStreamException
import java.io.Serializable

data class Person(var name: String, var email: String, var photoURL: String) : Serializable {
    private val TAG : String = "Person"

    companion object {
        private const val serialVersionUID = 1L
    }

    @Throws(IOException::class)
    private fun writeObject(outStream: java.io.ObjectOutputStream) {
        Log.d(TAG, "writeObject called")
        outStream.writeUTF(name)
        outStream.writeUTF(email)
        outStream.writeUTF(photoURL)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(inStream: java.io.ObjectInputStream) {
        Log.d(TAG, "readObject called")
        name = inStream.readUTF()
        email = inStream.readUTF()
        photoURL = inStream.readUTF()
    }

    @Throws(ObjectStreamException::class)
    private fun readObjectNoData() {
        Log.d(TAG, "readObjectNoData called")
    }
}
