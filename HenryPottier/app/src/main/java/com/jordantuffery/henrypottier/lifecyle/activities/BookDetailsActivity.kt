package com.jordantuffery.henrypottier.lifecyle.activities

import android.annotation.SuppressLint
import android.os.Bundle
import com.bumptech.glide.Glide
import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.lifecyle.DataRequestService
import com.jordantuffery.henrypottier.lifecyle.base.BaseActivity
import com.jordantuffery.henrypottier.model.objects.retrofit.RetrofitBook
import com.jordantuffery.henrypottier.utils.BookDetailEvent
import kotlinx.android.synthetic.main.activity_book_details.book_detail_back
import kotlinx.android.synthetic.main.activity_book_details.book_detail_cover
import kotlinx.android.synthetic.main.activity_book_details.book_detail_price
import kotlinx.android.synthetic.main.activity_book_details.book_detail_synopsis
import kotlinx.android.synthetic.main.activity_book_details.book_detail_title
import kotlinx.android.synthetic.main.activity_book_details.fab_add_shopping_list
import org.greenrobot.eventbus.Subscribe
import java.lang.StringBuilder
import java.text.NumberFormat

class BookDetailsActivity : BaseActivity() {
    override val layoutRes: Int = R.layout.activity_book_details

    var book: RetrofitBook? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        book_detail_back.setOnClickListener {
            setResult(RESULT_CODE_NONE)
            finish()
        }
        fab_add_shopping_list.setOnClickListener {
            val bookToAdd = book
            if (bookToAdd != null) {
                dataRequestService?.shoppingList?.addToShoppingList(bookToAdd)
                setResult(RESULT_CODE_ADDED_ITEM_IN_LIST)
            } else {
                setResult(RESULT_CODE_NONE)
            }
            finish()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onDataRequestServiceConnected(dataRequestService: DataRequestService) {
        super.onDataRequestServiceConnected(dataRequestService)
        val isbn = intent.extras.getString(EXTRA_BOOK_ISBN)
        dataRequestService.requestBookDetails(isbn)
    }

    @Subscribe(sticky = true)
    fun onEvent(event: BookDetailEvent) {
        book = event.retrofitBook
        Glide.with(this).load(book?.cover).into(book_detail_cover)
        book_detail_title.text = book?.title
        book_detail_price.text = NumberFormat.getCurrencyInstance().format(book?.price)
        book_detail_synopsis.text = StringBuilder().apply {
            book?.synopsis?.forEach {
                append(it)
                append("\n")
            }
        }.toString()
    }


    companion object {
        const val EXTRA_BOOK_ISBN = "EXTRA_BOOK_ISBN"

        const val RESULT_CODE_ADDED_ITEM_IN_LIST = 0
        const val RESULT_CODE_NONE = 1
    }
}