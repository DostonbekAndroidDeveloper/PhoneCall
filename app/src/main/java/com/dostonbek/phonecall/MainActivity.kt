package com.dostonbek.phonecall

import android.Manifest
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dostonbek.phonecall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val keyPadFragment = KeyPadFragment()
        val contactsFragment = ContactsFragment()
        makeCurrentFragment(
            keyPadFragment
        )

        binding.navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.keyPad -> makeCurrentFragment(keyPadFragment)
                R.id.contacts -> makeCurrentFragment(contactsFragment)
            }
            true
        }


    }



    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_activity_main, fragment)
            commit()

        }





}

