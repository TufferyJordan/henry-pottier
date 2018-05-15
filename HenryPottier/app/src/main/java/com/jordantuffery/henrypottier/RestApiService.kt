package com.jordantuffery.henrypottier

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.jordantuffery.henrypottier.model.BookDetailEvent
import com.jordantuffery.henrypottier.model.ListBooksEvent
import com.jordantuffery.henrypottier.model.RestInterface
import com.jordantuffery.henrypottier.model.RetrofitErrorEvent
import com.jordantuffery.henrypottier.model.objects.Book
import com.jordantuffery.henrypottier.utils.ServiceConnector
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class RestApiService : Service() {
    private val binder: IBinder = object : Binder(), ServiceConnector.Binder<RestApiService> {
        override val service: RestApiService
            get() = this@RestApiService
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://henri-potier.xebia.fr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RestInterface::class.java)!!

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        Timber.d("${javaClass.simpleName} created")
    }

    override fun onDestroy() {
        Timber.d("${javaClass.simpleName} destroyed")
        super.onDestroy()
    }

    fun requestBooks(callback: ((List<Book>) -> Unit)? = null) {
        Timber.e("request books")
        retrofit.getBooks().enqueue(object : Callback<List<Book>> {
            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                onRetrofitErrorEvent(t)
                EventBus.getDefault().post(RetrofitErrorEvent(t))
            }

            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                callback?.invoke(response.body())
                EventBus.getDefault().postSticky(ListBooksEvent(response.body()))
            }
        })
    }

    fun requestBookDetails(isbn: String, callback: ((Book?) -> Unit)? = null) {
        Timber.e("request Book details")
        retrofit.getBooks().enqueue(object : Callback<List<Book>> {
            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                onRetrofitErrorEvent(t)
                EventBus.getDefault().post(RetrofitErrorEvent(t))
            }

            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                val book: Book? = response.body().find { it.isbn == isbn }
                callback?.invoke(book)
                EventBus.getDefault().postSticky(BookDetailEvent(book))
            }
        })
    }

    private fun onRetrofitErrorEvent(t: Throwable) {
        Timber.e(t)
    }
}

