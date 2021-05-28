package com.example.edlink

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.edlink.fragment.*
import com.example.edlink.model.Users
import com.example.edlink.utilities.ModelPreferencesManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ModelPreferencesManager.with(application)
        val userData = ModelPreferencesManager.get<Users>("user")
        if (userData != null) {
            Log.d("CEK", "USERDATA ${userData.email}")
        }

        loadFragment(HomeFragment())

        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment-> {
                    loadFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.classesFragment-> {
                    loadFragment(ClassesFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.chatsFragment-> {
                    loadFragment(ChatsFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.exploreFragment-> {
                    loadFragment(ExploreFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.accountFragment-> {
                    loadFragment(AccountFragment())
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false

        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}