package com.example.edlink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.edlink.model.Users
import com.example.edlink.utilities.ModelPreferencesManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var thisUser: Users
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Profile"

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        initiateData()

        editProfileBtn.setOnClickListener {
            val intent = Intent (this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initiateData() {
        ModelPreferencesManager.with(application)
        val userData = ModelPreferencesManager.get<Users>("user")
        if (userData !== null)
            thisUser = userData

        fullnameUserText.text = thisUser.fullname
        emailUserText.text = thisUser.email
    }

    override fun onResume() {
        super.onResume()

        initiateData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}