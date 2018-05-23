package com.jordantuffery.henrypottier

import com.jordantuffery.henrypottier.restapi.Book
import com.jordantuffery.henrypottier.restapi.Offers
import com.jordantuffery.henrypottier.restapi.RestInterface
import com.jordantuffery.henrypottier.shoppinglist.ShoppingList
import com.jordantuffery.henrypottier.utils.BookDetailEvent
import com.jordantuffery.henrypottier.utils.ListBooksEvent
import com.jordantuffery.henrypottier.utils.ListOffersEvent
import com.jordantuffery.henrypottier.utils.RetrofitErrorEvent
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class DataRequestPresenterImpl(restInterface: RestInterface = Retrofit.Builder()
    .baseUrl(DataRequestPresenter.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(RestInterface::class.java)!!) : DataRequestPresenter {

    override val eventBus: EventBus = EventBus.getDefault()

    override val retrofit: RestInterface = restInterface

    override fun requestBooks(callback: ((List<Book>) -> Unit)?) {
        retrofit.getBooks().enqueue(object : Callback<List<Book>> {
            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Timber.e(t)
                EventBus.getDefault().post(RetrofitErrorEvent(t))
            }

            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                callback?.invoke(response.body())
                EventBus.getDefault().postSticky(ListBooksEvent(response.body()))
            }
        })
    }

    override fun requestBookDetails(isbn: String, callback: ((Book?) -> Unit)?) {
        retrofit.getBooks().enqueue(object : Callback<List<Book>> {
            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Timber.e(t)
                EventBus.getDefault().post(RetrofitErrorEvent(t))
            }

            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                val book: Book? = response.body().find { it.isbn == isbn }

                callback?.invoke(book)
                EventBus.getDefault().postSticky(BookDetailEvent(book))
            }
        })
    }

    override fun requestOffers(booksToApplyOffers: ShoppingList, callback: ((Offers) -> Unit)?) {
        if (booksToApplyOffers.isEmpty()) {
            EventBus.getDefault().post(ListOffersEvent(null))
            return
        }
        val isbnList = booksToApplyOffers.toLitteralList().map { it.isbn }.toTypedArray()

        val isbnString = StringBuilder().apply {
            for (i in 0 until isbnList.size) {
                append(isbnList[i])
                append(',')
            }
        }.toString()

        retrofit.getOffers(isbnString).enqueue(object : Callback<Offers> {
            override fun onFailure(call: Call<Offers>, t: Throwable) {
                Timber.e(t)
                EventBus.getDefault().post(RetrofitErrorEvent(t))
            }

            override fun onResponse(call: Call<Offers>, response: Response<Offers>) {
                val offers: Offers = response.body()
                callback?.invoke(offers)
                EventBus.getDefault().post(ListOffersEvent(offers))
            }
        })
    }

}

interface DataRequestPresenter {
    val eventBus: EventBus
    val retrofit: RestInterface

    fun requestBooks(callback: ((List<Book>) -> Unit)? = null)
    fun requestBookDetails(isbn: String, callback: ((Book?) -> Unit)? = null)
    fun requestOffers(booksToApplyOffers: ShoppingList, callback: ((Offers) -> Unit)? = null)

    companion object {
        const val BASE_URL = "http://henri-potier.xebia.fr/"
    }
}
