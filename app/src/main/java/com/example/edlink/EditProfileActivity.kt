package com.example.edlink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.edlink.model.Users
import com.example.edlink.utilities.ModelPreferencesManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.inputEmailUser
import kotlinx.android.synthetic.main.activity_edit_profile.inputFullnameUser
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.toolbar

class EditProfileActivity : AppCompatActivity() {
    private lateinit var thisUser: Users
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Edit Profile"

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        ModelPreferencesManager.with(application)
        database = Firebase.database.reference
        val userData = ModelPreferencesManager.get<Users>("user")
        if (userData !== null)
            thisUser = userData

        inputFullnameUser.setText(thisUser.fullname)
        inputEmailUser.setText(thisUser.email)

        saveEditProfileBtn.setOnClickListener {
            doUpdateProfile()
        }
    }

    private fun doUpdateProfile() {
        val newFullname = inputFullnameUser.text.toString()
        if (newFullname.isEmpty()) {
            return Toast.makeText(this, "Silahkan isi Fullname terlebih dahulu", Toast.LENGTH_LONG).show()
        } else if (newFullname.equals(thisUser.fullname)) {
            return Toast.makeText(this, "Fullname tidak boleh sama dengan yg sebelumnya", Toast.LENGTH_LONG).show()
        } else {
            val user = Users(thisUser.id, thisUser.email, newFullname, thisUser.password)
            database.child("users").child(thisUser.id).setValue(user).addOnCompleteListener {
                Toast.makeText(this, "Successs Update Profile",Toast.LENGTH_SHORT).show()
                inputFullnameUser.setText(newFullname)
                ModelPreferencesManager.put(user, "user")
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}