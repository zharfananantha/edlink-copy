package com.example.edlink.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edlink.AddPostingActivity
import com.example.edlink.R
import com.example.edlink.SignUpActivity
import com.example.edlink.adapter.PostingAdapter
import com.example.edlink.model.Posting
import com.example.edlink.model.Users
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<PostingAdapter.ViewHolder>? = null
    private lateinit var database: DatabaseReference
    private lateinit var list : MutableList<Posting>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = "Home"
        database = Firebase.database.reference
        list = mutableListOf()

        initData()

        addPostFab.setOnClickListener {
            activity?.let{
                val intent = Intent (it, AddPostingActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    private fun initData() {
        database.child("postings").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    for (h in p0.children) {
                        val post = h.getValue(Posting::class.java)
                        list.add(post!!)
                    }
                    if (activity !== null) {
                        postingRecyclerView.apply {
                            layoutManager = LinearLayoutManager(activity!!)
                            adapter = PostingAdapter(list, activity!!)
                        }
                    }
                    list.forEach { post ->
                        Log.d("CEK", "Posting Text ${post.contentText}")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}