package com.jordantuffery.henrypottier.shoppinglist

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jordantuffery.henrypottier.DataRequestService
import com.jordantuffery.henrypottier.utils.ListOffersEvent
import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.restapi.Book
import com.jordantuffery.henrypottier.utils.ShoppingListChangeEvent
import com.jordantuffery.henrypottier.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_shopping_list.shopping_list_recycler_view
import kotlinx.android.synthetic.main.fragment_shopping_list.shopping_list_text_new_price
import kotlinx.android.synthetic.main.fragment_shopping_list.shopping_list_text_old_price
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber
import java.text.NumberFormat

class ShoppingListFragment : BaseFragment(), ShoppingListAdapter.OnRemoveItemListener {

    override val layoutRes: Int = R.layout.fragment_shopping_list

    private val adapter: ShoppingListAdapter = ShoppingListAdapter(
        ShoppingList()).apply {
        listener = this@ShoppingListFragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shopping_list_recycler_view.layoutManager = LinearLayoutManager(context)
        shopping_list_recycler_view.addItemDecoration(
            DividerItemDecoration(shopping_list_recycler_view.context, DividerItemDecoration.VERTICAL))
        shopping_list_recycler_view.adapter = adapter
        shopping_list_text_old_price.paintFlags = shopping_list_text_old_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    @SuppressLint("MissingSuperCall")
    override fun onDataRequestServiceConnected(dataRequestService: DataRequestService) {
        super.onDataRequestServiceConnected(dataRequestService)

        dataRequestService.requestOffers(dataRequestService.shoppingList)

        adapter.apply {
            shoppingList = dataRequestService.shoppingList
            notifyDataSetChanged()
        }
    }

    @Subscribe
    fun onEvent(event: ShoppingListChangeEvent) {

        dataRequestService?.requestOffers(event.shoppingList)

        adapter.apply {
            shoppingList = event.shoppingList
            notifyDataSetChanged()
        }
    }

    @Subscribe
    fun onEvent(event: ListOffersEvent) {
        Timber.e("received offers")
        val currency = NumberFormat.getCurrencyInstance()
        shopping_list_text_old_price.text = currency.format(dataRequestService?.shoppingList?.sum())
        if (event.list != null) {
            shopping_list_text_new_price.text = currency.format(
                event.list.applyOffers(dataRequestService?.shoppingList?.sum()))
        } else {
            shopping_list_text_new_price.text = currency.format(dataRequestService?.shoppingList?.sum())
        }
    }

    override fun onRemoveItem(view: View, book: Book) {
        dataRequestService?.shoppingList?.removeFromShoppingList(book)
    }

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }
}