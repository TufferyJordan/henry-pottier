package com.jordantuffery.henrypottier.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.model.objects.Book
import kotlinx.android.synthetic.main.view_library_item.view.book_description
import kotlinx.android.synthetic.main.view_library_item.view.book_go_to_detail_button
import kotlinx.android.synthetic.main.view_library_item.view.book_image
import kotlinx.android.synthetic.main.view_library_item.view.book_title
import timber.log.Timber

class LibraryAdapter(var itemList: List<Book>) : RecyclerView.Adapter<LibraryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder = LibraryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_library_item, parent, false))

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        Glide.with(holder.bookCover).load(itemList[position].cover).into(holder.bookCover)
        holder.title.text = itemList[position].title
        holder.synopsis.text = itemList[position].synopsis[0]
        holder.button.setOnClickListener {
            Timber.e("isbn : %s", itemList[position].isbn)
        }
    }
}

class LibraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val bookCover: ImageView = itemView.book_image
    val title: TextView = itemView.book_title
    val synopsis: TextView = itemView.book_description
    val button: ImageView = itemView.book_go_to_detail_button
}