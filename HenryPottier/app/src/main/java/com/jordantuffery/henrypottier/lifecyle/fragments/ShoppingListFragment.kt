package com.jordantuffery.henrypottier.lifecyle.fragments

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.lifecyle.DataRequestService
import com.jordantuffery.henrypottier.lifecyle.ShoppingList
import com.jordantuffery.henrypottier.lifecyle.ShoppingListAdapter
import com.jordantuffery.henrypottier.lifecyle.base.BaseFragment
import com.jordantuffery.henrypottier.model.objects.retrofit.RetrofitBook
import com.jordantuffery.henrypottier.utils.ShoppingListChangeEvent
import kotlinx.android.synthetic.main.fragment_shopping_list.shopping_list_recycler_view
import kotlinx.android.synthetic.main.fragment_shopping_list.shopping_list_text_new_price
import kotlinx.android.synthetic.main.fragment_shopping_list.shopping_list_text_old_price
import org.greenrobot.eventbus.Subscribe
import java.text.NumberFormat

class ShoppingListFragment : BaseFragment(), ShoppingListAdapter.OnRemoveItemListener {

    override val layoutRes: Int = R.layout.fragment_shopping_list

    private val adapter: ShoppingListAdapter = ShoppingListAdapter(ShoppingList()).apply {
        listener = this@ShoppingListFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shopping_list_recycler_view.layoutManager = LinearLayoutManager(context)
        shopping_list_recycler_view.addItemDecoration(
            DividerItemDecoration(shopping_list_recycler_view.context, DividerItemDecoration.HORIZONTAL))
        shopping_list_recycler_view.adapter = adapter
        shopping_list_text_old_price.paintFlags = shopping_list_text_old_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    @SuppressLint("MissingSuperCall")
    override fun onDataRequestServiceConnected(dataRequestService: DataRequestService) {
        super.onDataRequestServiceConnected(dataRequestService)
        adapter.apply {
            shoppingList = dataRequestService.shoppingList
            notifyDataSetChanged()
        }
        shopping_list_text_old_price.text = NumberFormat.getCurrencyInstance().format(
            dataRequestService.shoppingList.sum())
        shopping_list_text_new_price.text = NumberFormat.getCurrencyInstance().format(
            dataRequestService.shoppingList.sum())
    }

    @Subscribe
    fun onEvent(event: ShoppingListChangeEvent) {
        adapter.apply {
            shoppingList = event.shoppingList
            notifyDataSetChanged()
        }
        shopping_list_text_old_price.text = NumberFormat.getCurrencyInstance().format(event.shoppingList.sum())
        shopping_list_text_new_price.text = NumberFormat.getCurrencyInstance().format(event.shoppingList.sum())
    }

    override fun onRemoveItem(view: View, book: RetrofitBook) {
        dataRequestService?.shoppingList?.removeFromShoppingList(book)
    }

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }
}