package com.example.android.contactsapp.objects

import android.os.Parcel
import android.os.Parcelable

data class Contact(val name: String?, val email: String?, val photoURL: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        name = parcel.readString(),
        email = parcel.readString(),
        photoURL = parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(photoURL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}
