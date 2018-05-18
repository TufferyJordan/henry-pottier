package com.jordantuffery.henrypottier.lifecyle

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.jordantuffery.henrypottier.model.objects.retrofit.Offers
import com.jordantuffery.henrypottier.model.objects.retrofit.RetrofitBook
import com.jordantuffery.henrypottier.utils.BookDetailEvent
import com.jordantuffery.henrypottier.utils.ListBooksEvent
import com.jordantuffery.henrypottier.utils.ListOffersEvent
import com.jordantuffery.henrypottier.utils.RestInterface
import com.jordantuffery.henrypottier.utils.RetrofitErrorEvent
import com.jordantuffery.henrypottier.utils.ServiceConnector
import com.jordantuffery.henrypottier.utils.ShoppingListChangeEvent
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class DataRequestService : Service(), ShoppingList.OnShoppingListChange {
    private val binder: IBinder = object : Binder(), ServiceConnector.Binder<DataRequestService> {
        override val service: DataRequestService
            get() = this@DataRequestService
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://henri-potier.xebia.fr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RestInterface::class.java)!!

    val shoppingList = ShoppingList().apply {
        listener = this@DataRequestService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        Timber.d("${javaClass.simpleName} created")
    }

    override fun onDestroy() {
        Timber.d("${javaClass.simpleName} destroyed")
        super.onDestroy()
    }

    fun requestBooks(callback: ((List<RetrofitBook>) -> Unit)? = null) {
        Timber.e("request books")
        retrofit.getBooks().enqueue(object : Callback<List<RetrofitBook>> {
            override fun onFailure(call: Call<List<RetrofitBook>>, t: Throwable) {
                Timber.e(t)
                EventBus.getDefault().post(RetrofitErrorEvent(t))
            }

            override fun onResponse(call: Call<List<RetrofitBook>>, response: Response<List<RetrofitBook>>) {
                callback?.invoke(response.body())
                EventBus.getDefault().postSticky(ListBooksEvent(response.body()))
            }
        })
    }

    fun requestBookDetails(isbn: String, callback: ((RetrofitBook?) -> Unit)? = null) {
        Timber.e("request RetrofitBook details")
        retrofit.getBooks().enqueue(object : Callback<List<RetrofitBook>> {
            override fun onFailure(call: Call<List<RetrofitBook>>, t: Throwable) {
                Timber.e(t)
                EventBus.getDefault().post(RetrofitErrorEvent(t))
            }

            override fun onResponse(call: Call<List<RetrofitBook>>, response: Response<List<RetrofitBook>>) {
                val retrofitBook: RetrofitBook? = response.body().find { it.isbn == isbn }

                callback?.invoke(retrofitBook)
                EventBus.getDefault().postSticky(BookDetailEvent(retrofitBook))
            }
        })
    }

    fun requestOffers(booksToApplyOffers: ShoppingList, callback: ((Offers) -> Unit)? = null) {
        Timber.e("request offers")
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

    override fun onShoppingListChange(newList: ShoppingList) {
        EventBus.getDefault().post(ShoppingListChangeEvent(newList))
    }
}

class ShoppingList(var listener: OnShoppingListChange? = null) : ArrayList<ShoppingList.ShoppingItem>() {

    fun addToShoppingList(itemToAdd: RetrofitBook) {
        if (map { it.item.isbn }.contains(itemToAdd.isbn)) {
            forEach {
                if (it.item.isbn == itemToAdd.isbn) it.number++
            }
        } else {
            add(ShoppingItem(itemToAdd, 1))
        }
        listener?.onShoppingListChange(this)
    }

    @Synchronized
    fun removeFromShoppingList(itemToRemove: RetrofitBook) {
        if (map { it.item.isbn }.contains(itemToRemove.isbn)) {
            var indexToRemove = -1
            for (index in 0 until size) {
                if (this[index].item.isbn == itemToRemove.isbn) {
                    if (this[index].number == 1) {
                        indexToRemove = index
                    } else {
                        this[index].number--
                    }
                }
            }
            if (indexToRemove != -1) {
                removeAt(indexToRemove)
            }
        }
        listener?.onShoppingListChange(this)
    }

    fun toLitteralList(): ArrayList<RetrofitBook> {
        val realList: ArrayList<RetrofitBook> = arrayListOf()
        for (shoppingItem in this) {
            for (i in 0 until shoppingItem.number) {
                realList.add(shoppingItem.item)
            }
        }
        return realList
    }

    fun sum(): Float {
        var sum: Float = 0f
        forEach {
            for (i in 0 until it.number)
                sum += it.item.price
        }
        return sum
    }

    interface OnShoppingListChange {
        fun onShoppingListChange(newList: ShoppingList)
    }

    class ShoppingItem(val item: RetrofitBook, var number: Int)
}