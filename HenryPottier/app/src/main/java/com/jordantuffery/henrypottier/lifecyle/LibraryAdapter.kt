package com.jordantuffery.henrypottier.lifecyle

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.model.objects.retrofit.RetrofitBook
import kotlinx.android.synthetic.main.item_library.view.book_image
import kotlinx.android.synthetic.main.item_library.view.book_image_loading
import kotlinx.android.synthetic.main.item_library.view.book_title

class LibraryAdapter(var itemList: List<RetrofitBook>) : RecyclerView.Adapter<LibraryViewHolder>() {
    var listener: OnItemClickListener? = null
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder = LibraryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_library, parent, false))

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        Glide.with(holder.bookCover).load(itemList[position].cover).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                                      isFirstResource: Boolean): Boolean {
                holder.progressBar.visibility = View.GONE
                holder.bookCover.setImageResource(R.drawable.ic_image_not_found)
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                         dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                holder.progressBar.visibility = View.GONE
                return false
            }
        }).into(holder.bookCover)
        holder.title.text = itemList[position].title
        holder.itemView.setOnClickListener { view ->
            listener?.onItemClick(view, itemList[position])
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, book: RetrofitBook)
    }
}

class LibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val bookCover: ImageView = itemView.book_image
    val progressBar: ProgressBar = itemView.book_image_loading
    val title: TextView = itemView.book_title
}