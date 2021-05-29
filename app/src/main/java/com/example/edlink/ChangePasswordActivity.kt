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
import kotlinx.android.synthetic.main.activity_profile.toolbar

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var thisUser: Users
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Change Password"

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

        changePassBtn.setOnClickListener {
            doUpdate()
        }
    }

    private fun doUpdate() {
        val oldPass = inputFullnameUser.text.toString()
        val newPass = inputEmailUser.text.toString()
        val retypeNewPass = inputRetypeNewPassword.text.toString()

        if (oldPass.isEmpty()) {
            return Toast.makeText(this, "Silahkan isi Old Password terlebih dahulu", Toast.LENGTH_LONG).show()
        } else if (!oldPass.equals(thisUser.password)) {
            return Toast.makeText(this, "Old Password salah", Toast.LENGTH_LONG).show()
        } else if (newPass.isEmpty()) {
            return Toast.makeText(this, "Silahkan isi New Password terlebih dahulu", Toast.LENGTH_LONG).show()
        } else if (newPass.equals(thisUser.password)) {
            return Toast.makeText(this, "New Password tidak boleh sama dengan Old Password", Toast.LENGTH_LONG).show()
        } else if (retypeNewPass.isEmpty()) {
            return Toast.makeText(this, "Silahkan isi Retype New Password terlebih dahulu", Toast.LENGTH_LONG).show()
        } else if (!newPass.equals(retypeNewPass)) {
            return Toast.makeText(this, "New Password dan Retype New Password tidak sama", Toast.LENGTH_LONG).show()
        } else {
            val user = Users(thisUser.id, thisUser.email, thisUser.fullname, newPass)
            database.child("users").child(thisUser.id).setValue(user).addOnCompleteListener {
                Toast.makeText(this, "Successs Change Password",Toast.LENGTH_SHORT).show()
                inputFullnameUser.setText("")
                inputEmailUser.setText("")
                inputRetypeNewPassword.setText("")
                ModelPreferencesManager.put(null, "user")
                val intent = Intent (this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}