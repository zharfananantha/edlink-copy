package com.example.edlink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.edlink.model.Posting
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_posting.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.toolbar
import java.text.SimpleDateFormat

class DetailPostingActivity : AppCompatActivity() {
    private lateinit var dataPosting: Posting
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_posting)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Information Detail"

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        val getData:String = intent.getStringExtra("DetailPosting").toString()
        dataPosting = Gson().fromJson(getData, Posting::class.java)

        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
        val dateString = simpleDateFormat.format(dataPosting.date.toLong())

        if (dataPosting.contentImage.equals("")) {
            mainContentImage.visibility = View.GONE
        } else {
            Glide.with(this)
                .load(dataPosting.contentImage)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(mainContentImage)
        }

        nameLabel.text = dataPosting.fullname
        mainContentText.text = dataPosting.contentText
        dateLabel.text = dateString

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}