package com.jordantuffery.henrypottier

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.jordantuffery.henrypottier.restapi.Offers
import com.jordantuffery.henrypottier.restapi.Book
import com.jordantuffery.henrypottier.shoppinglist.ShoppingList
import com.jordantuffery.henrypottier.utils.BookDetailEvent
import com.jordantuffery.henrypottier.utils.ListBooksEvent
import com.jordantuffery.henrypottier.utils.ListOffersEvent
import com.jordantuffery.henrypottier.utils.RetrofitErrorEvent
import com.jordantuffery.henrypottier.utils.ServiceConnector
import com.jordantuffery.henrypottier.utils.ShoppingListChangeEvent
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class DataRequestService : Service(), ShoppingList.OnShoppingListChange {
    private val binder: IBinder = object : Binder(), ServiceConnector.Binder<DataRequestService> {
        override val service: DataRequestService
            get() = this@DataRequestService
    }

    val presenter = DataRequestPresenterImpl()

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

    fun requestBooks(callback: ((List<Book>) -> Unit)? = null) {
        Timber.e("request books")
        presenter.requestBooks(callback)
    }

    fun requestBookDetails(isbn: String, callback: ((Book?) -> Unit)? = null) {
        Timber.e("request Book details")
        presenter.requestBookDetails(isbn,callback)
    }

    fun requestOffers(booksToApplyOffers: ShoppingList, callback: ((Offers) -> Unit)? = null) {
        Timber.e("request offers")
        presenter.requestOffers(booksToApplyOffers,callback)
    }

    override fun onShoppingListChange(newList: ShoppingList) {
        EventBus.getDefault().post(ShoppingListChangeEvent(newList))
    }
}
