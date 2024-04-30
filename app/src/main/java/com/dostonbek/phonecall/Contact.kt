package com.dostonbek.phonecall
import android.net.Uri

data class Contact(
    val name: String,
    val phoneNumber: String,
    val photoUri: Uri?
)

