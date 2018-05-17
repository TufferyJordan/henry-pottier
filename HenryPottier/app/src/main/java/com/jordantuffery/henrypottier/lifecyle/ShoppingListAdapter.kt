package com.jordantuffery.henrypottier.lifecyle

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.model.objects.retrofit.RetrofitBook
import kotlinx.android.synthetic.main.item_shopping_list.view.shopping_item_cover
import kotlinx.android.synthetic.main.item_shopping_list.view.shopping_item_number
import kotlinx.android.synthetic.main.item_shopping_list.view.shopping_item_price
import kotlinx.android.synthetic.main.item_shopping_list.view.shopping_item_remove_item
import kotlinx.android.synthetic.main.item_shopping_list.view.shopping_item_title
import java.text.NumberFormat

class ShoppingListAdapter(
    var shoppingList: ShoppingList) : RecyclerView.Adapter<ShoppingListViewHolder>() {

    var listener: OnRemoveItemListener? = null
        set(value) {
            notifyDataSetChanged()
            field = value
        }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder = ShoppingListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_list, parent, false))

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        Glide.with(holder.bookCover).load(shoppingList[position].item.cover).into(holder.bookCover)
        holder.title.text = shoppingList[position].item.title
        holder.price.text = NumberFormat.getCurrencyInstance().format(shoppingList[position].item.price)
        holder.number.text = holder.number.resources.getString(R.string.number_of_items).format(
            shoppingList[position].number)
        holder.removeButton.setOnClickListener { listener?.onRemoveItem(it, shoppingList[position].item) }
    }

    interface OnRemoveItemListener {
        fun onRemoveItem(view: View, book: RetrofitBook)
    }
}

class ShoppingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val bookCover: ImageView = itemView.shopping_item_cover
    val title: TextView = itemView.shopping_item_title
    val price: TextView = itemView.shopping_item_price
    val number: TextView = itemView.shopping_item_number
    val removeButton: ImageView = itemView.shopping_item_remove_item
}