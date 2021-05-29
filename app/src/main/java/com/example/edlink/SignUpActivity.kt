package com.example.edlink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.edlink.model.Users
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.toolbar

class SignUpActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    lateinit var list : MutableList<Users>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = ""

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        database = Firebase.database.reference
        list = mutableListOf()

        signupBtn.setOnClickListener {
            savedata()
        }
        gotoLoginLabel.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun savedata() {
        val nama = inputFullName.text.toString()
        val email = inputEmail.text.toString()
        val pass = inputPassword.text.toString()
        val retypePass = inputRetypePassword.text.toString()

        if (email.isEmpty()) {
            return Toast.makeText(this, "Silahkan isi Email terlebih dahulu",Toast.LENGTH_LONG).show()
        } else if (nama.isEmpty()) {
            return Toast.makeText(this, "Silahkan isi Fullname terlebih dahulu",Toast.LENGTH_LONG).show()
        } else if (pass.isEmpty()) {
            return Toast.makeText(this, "Silahkan isi Password terlebih dahulu",Toast.LENGTH_LONG).show()
        } else if (retypePass.isEmpty()) {
            return Toast.makeText(this, "Silahkan isi Retype Password terlebih dahulu",Toast.LENGTH_LONG).show()
        } else if (!pass.equals(retypePass)) {
            return Toast.makeText(this, "Password dan Retype Password tidak sama",Toast.LENGTH_LONG).show()
        } else {
            val userId = database.push().key.toString()
            val user = Users(userId,email,nama,pass)
            database.child("users").addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var counter = 0
                    if (p0!!.exists()){
                        for (h in p0.children){
                            val user = h.getValue(Users::class.java)
                            list.add(user!!)
                        }
                        list.forEach { user ->
                            if (user.email.equals(email, true)) {
                                counter++
//                                Log.d("CEK", "Counter $counter")
                            }
                        }
                        if (counter == 0) {
                            addToDB(userId, user)
                        } else {
                            emailRegistered()
                        }
                    }
                }
            })
        }
    }

    private fun addToDB(userId: String, user: Users) {
        database.child("users").child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Successs Input Data",Toast.LENGTH_SHORT).show()
            inputFullName.setText("")
            inputEmail.setText("")
            inputPassword.setText("")
            inputRetypePassword.setText("")
        }
    }

    private fun emailRegistered() {
        Toast.makeText(this, "Email Sudah Terdaftar",Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}