package com.example.edlink

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.edlink.model.Users
import com.example.edlink.utilities.ModelPreferencesManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.inputEmail
import kotlinx.android.synthetic.main.activity_login.inputPassword


class LoginActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var list : MutableList<Users>
    private lateinit var userChoosen: Users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = ""

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        ModelPreferencesManager.with(application)
        database = Firebase.database.reference
        list = mutableListOf()
        doInitialize()

        loginBtn.setOnClickListener {
            doLogin()
        }

        gotoSignUpLabel.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun doLogin() {
        val email = inputEmail.text.toString()
        val pass = inputPassword.text.toString()
        if (email.isEmpty()) {
            return Toast.makeText(this, "Silahkan isi Email terlebih dahulu", Toast.LENGTH_LONG).show()
        } else if (pass.isEmpty()) {
            return Toast.makeText(this, "Silahkan isi Password terlebih dahulu", Toast.LENGTH_LONG).show()
        } else if (list.isNotEmpty()) {
            var counter = 0
            list.forEach { user ->
                if (user.email.equals(email, true) && user.password.equals(pass, true)) {
                    counter++
                    userChoosen = user
                }
            }
            if (counter != 0) {
                return gotoHome()
            } else {
                return Toast.makeText(this, "Email atau Password Salah", Toast.LENGTH_LONG).show()
            }
        } else {
            return Toast.makeText(this, "Tidak ada data yang dapat ditemukan", Toast.LENGTH_LONG).show()
        }
    }

    private fun gotoHome() {
        ModelPreferencesManager.put(userChoosen, "user")
        Toast.makeText(this, "Berhasil Login", Toast.LENGTH_LONG).show()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun doInitialize() {
        database.child("users").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    for (h in p0.children) {
                        val user = h.getValue(Users::class.java)
                        list.add(user!!)
                    }
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}