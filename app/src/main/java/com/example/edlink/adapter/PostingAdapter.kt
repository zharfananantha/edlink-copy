package com.example.edlink.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.edlink.R
import com.example.edlink.model.Posting
import kotlinx.android.synthetic.main.posting_item.view.*
import java.text.SimpleDateFormat
import java.util.*


class PostingAdapter(private val list: List<Posting>, private val ctx: Context) : RecyclerView.Adapter<PostingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(ctx).inflate(R.layout.posting_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
        val dateString = simpleDateFormat.format(item.date.toLong())
//        val formatDate = "dd MMM yyyy hh:mm:ss"
//        val formatter: SimpleDateFormat = SimpleDateFormat(formatDate)
//        val calendar: Calendar = Calendar.getInstance()
//        calendar.setTimeInMillis(item.date.toLong())
//        val theDate = formatter.format(calendar.getTime())
        Log.d("CEK", "CONTENT IMAGE ${item.contentImage}")
        Glide.with(ctx)
            .load(item.contentImage)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .dontAnimate()
            .into(holder.imgContent);
//        val myUri: Uri = Uri.parse(item.contentImage)

        holder.nama.text = item.fullname
        holder.txtContent.text = item.contentText
        holder.tgl.text = dateString
//        holder.imgContent.setImageURI(myUri)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nama = view.nameLabel
        var tgl = view.dateLabel
        var imgContent = view.mainContentImage
        var txtContent = view.mainContentText
    }
}


