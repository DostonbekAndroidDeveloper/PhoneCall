package com.dostonbek.phonecall

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dostonbek.phonecall.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {
    private var READ_CONTACTS_PERMISSION_CODE = 123
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter
    private lateinit var binding: FragmentContactsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentContactsBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ContactAdapter { phoneNumber ->
            dialPhoneNumber(phoneNumber)
        }
        recyclerView.adapter = adapter
        checkPermissionNumber()
        checkPermission()
        return binding.root
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.READ_CONTACTS),
                READ_CONTACTS_PERMISSION_CODE)
        } else {
            readContacts()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_CONTACTS_PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    readContacts()
                } else {
                    // Permission denied
                }
                return
            }
        }
    }


    @SuppressLint("Range")
    private fun readContacts() {
        val contactsWithName = mutableListOf<Contact>()
        val contactsWithNumberOnly = mutableListOf<Contact>()

        val cursor: Cursor? = requireActivity().contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

        cursor?.let {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndex(ContactsContract.Contacts._ID))
                val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                val phoneCursor = requireActivity().contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    arrayOf(id),
                    null
                )

                phoneCursor?.let { phoneCursor ->
                    if (phoneCursor.moveToFirst()) {
                        val phoneNumber =
                            phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        val photoUriString =
                            it.getString(it.getColumnIndex(ContactsContract.Contacts.PHOTO_URI))
                        val photoUri = if (!photoUriString.isNullOrEmpty()) Uri.parse(photoUriString) else null
                        val contact = Contact(name, phoneNumber, photoUri)
                        if (phoneNumber.isNotEmpty()||name.isNotEmpty()) {
                            contactsWithName.add(contact)
                        }
                    } else {
                        // If no name, add it to contactsWithNumberOnly list
                        val phoneNumber =
                            it.getString(it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        if (name.isEmpty()) {
                            val contact = Contact(name, phoneNumber, null)
                            contactsWithNumberOnly.add(contact)
                        }
                    }
                    phoneCursor.close()
                }
            }
            it.close()
        }

        // Combine all lists, with contactsWithNumberOnly appearing last
        val sortedContactsList = mutableListOf<Contact>()
        sortedContactsList.addAll(contactsWithName)
        sortedContactsList.addAll(contactsWithNumberOnly)

        adapter.setData(sortedContactsList)
    }






    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }
    private fun checkPermissionNumber(){
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE),101)

        }

    }

}