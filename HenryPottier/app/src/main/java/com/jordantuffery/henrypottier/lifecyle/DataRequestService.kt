package com.jordantuffery.henrypottier.lifecyle

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.jordantuffery.henrypottier.model.objects.retrofit.RetrofitBook
import com.jordantuffery.henrypottier.utils.BookDetailEvent
import com.jordantuffery.henrypottier.utils.ListBooksEvent
import com.jordantuffery.henrypottier.utils.RestInterface
import com.jordantuffery.henrypottier.utils.RetrofitErrorEvent
import com.jordantuffery.henrypottier.utils.ServiceConnector
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class DataRequestService : Service() {
    private val binder: IBinder = object : Binder(), ServiceConnector.Binder<DataRequestService> {
        override val service: DataRequestService
            get() = this@DataRequestService
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://henri-potier.xebia.fr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RestInterface::class.java)!!

    private val shoppingList = hashSetOf<RetrofitBook>()

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
                shoppingList.filter { response.body().contains(it) }.forEach {shoppingList.remove(it)}
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

    fun addBookToShoppingList(retrofitBookToAdd: RetrofitBook) {
        shoppingList.add(retrofitBookToAdd)
    }

    fun removeBookFromShoppingList(retrofitBookToRemove: RetrofitBook) {
        shoppingList.remove(retrofitBookToRemove)
    }

    fun getShoppingList(): HashSet<RetrofitBook> = shoppingList
}

