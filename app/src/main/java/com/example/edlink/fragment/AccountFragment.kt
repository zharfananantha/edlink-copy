package com.example.edlink.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.edlink.*
import com.example.edlink.utilities.ModelPreferencesManager
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_home.toolbar

class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { ModelPreferencesManager.with(it.application) }
        toolbar.title = "Others"

        manageProfileLayout.setOnClickListener {
            activity?.let{
                val intent = Intent (it, ProfileActivity::class.java)
                it.startActivity(intent)
            }
        }

        mediaLayout.setOnClickListener {
            activity?.let{
                val intent = Intent (it, MediaLibraryActivity::class.java)
                it.startActivity(intent)
            }
        }

        settingLayout.setOnClickListener {
            activity?.let{
                val intent = Intent (it, SettingsActivity::class.java)
                it.startActivity(intent)
            }
        }

        exitLayout.setOnClickListener {
            ModelPreferencesManager.put(null, "user")
            activity?.let{
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
                it.finish()
            }
        }
    }

}