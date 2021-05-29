package com.example.edlink.adapter

import android.content.Context
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


class PostingAdapter(
    private val list: List<Posting>,
    private val ctx: Context,
    private val onItemClicked: (Posting) -> Unit
) : RecyclerView.Adapter<PostingAdapter.ViewHolder>() {

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

        Log.d("CEK", "CONTENT IMAGE ${item.contentImage}")
        if (item.contentImage.equals("")) {
            holder.imgContent.visibility = View.GONE
        } else {
            Glide.with(ctx)
                .load(item.contentImage)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(holder.imgContent)
        }

        holder.nama.text = item.fullname
        holder.txtContent.text = item.contentText
        holder.tgl.text = dateString

        holder.itemLayout.setOnClickListener {
            onItemClicked(item)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nama = view.nameLabel
        var tgl = view.dateLabel
        var imgContent = view.mainContentImage
        var txtContent = view.mainContentText
        var itemLayout = view.postingLayout
    }


}


