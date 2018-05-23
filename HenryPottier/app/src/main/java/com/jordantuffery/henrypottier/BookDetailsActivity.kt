package com.jordantuffery.henrypottier

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.jordantuffery.henrypottier.base.BaseActivity
import com.jordantuffery.henrypottier.restapi.Book
import com.jordantuffery.henrypottier.shoppinglist.ShoppingModel
import com.jordantuffery.henrypottier.utils.BookDetailEvent
import com.jordantuffery.henrypottier.utils.ConnectivityChangeEvent
import com.jordantuffery.henrypottier.utils.RetrofitErrorEvent
import kotlinx.android.synthetic.main.activity_book_details.book_detail_back
import kotlinx.android.synthetic.main.activity_book_details.book_detail_cover
import kotlinx.android.synthetic.main.activity_book_details.book_detail_no_connection_image
import kotlinx.android.synthetic.main.activity_book_details.book_detail_no_connection_layout
import kotlinx.android.synthetic.main.activity_book_details.book_detail_price
import kotlinx.android.synthetic.main.activity_book_details.book_detail_scroll_view
import kotlinx.android.synthetic.main.activity_book_details.book_detail_synopsis
import kotlinx.android.synthetic.main.activity_book_details.book_detail_title
import kotlinx.android.synthetic.main.activity_book_details.fab_add_shopping_list
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.StringBuilder
import java.text.NumberFormat

class BookDetailsActivity : BaseActivity() {
    override val layoutRes: Int = R.layout.activity_book_details

    private var mBook: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        book_detail_back.setOnClickListener {
            setResult(RESULT_CODE_NONE)
            finish()
        }
        fab_add_shopping_list.setOnClickListener {
            val bookToAdd = mBook
            if (bookToAdd != null) {
                ShoppingModel.shoppingList.addToShoppingList(bookToAdd)
                setResult(RESULT_CODE_ADDED_ITEM_IN_LIST)
            } else {
                setResult(RESULT_CODE_NONE)
            }
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val isbn = intent.extras.getString(EXTRA_BOOK_ISBN)
        presenter?.requestBookDetails(isbn)
    }

    @Subscribe(sticky = true)
    fun onEvent(event: BookDetailEvent) {
        mBook = event.book
        Glide.with(this).load(mBook?.cover).into(book_detail_cover)
        book_detail_title.text = mBook?.title
        book_detail_price.text = NumberFormat.getCurrencyInstance().format(mBook?.price)
        book_detail_synopsis.text = StringBuilder().apply {
            mBook?.synopsis?.forEach {
                append(it)
                append("\n")
            }
        }.toString()
    }

    @Subscribe
    fun onEvent(event: RetrofitErrorEvent) {
        updateUI(false)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onEvent(event: ConnectivityChangeEvent) {
        updateUI(event.connected)
    }


    private fun updateUI(withConnection: Boolean?) {
        if (withConnection == null) return
        if (withConnection) {
            book_detail_scroll_view.visibility = View.VISIBLE
            fab_add_shopping_list.visibility = View.VISIBLE
            book_detail_no_connection_layout.visibility = View.GONE
        } else {
            book_detail_scroll_view.visibility = View.GONE
            fab_add_shopping_list.visibility = View.GONE
            book_detail_no_connection_layout.visibility = View.VISIBLE
            book_detail_no_connection_image.setOnClickListener {
                presenter?.requestBooks {
                    updateUI(true)
                }
            }
        }
    }

    companion object {
        const val EXTRA_BOOK_ISBN = "EXTRA_BOOK_ISBN"

        const val RESULT_CODE_ADDED_ITEM_IN_LIST = 0
        const val RESULT_CODE_NONE = 1
    }
}