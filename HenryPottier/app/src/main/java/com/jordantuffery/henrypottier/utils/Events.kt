package com.jordantuffery.henrypottier.utils

import com.jordantuffery.henrypottier.lifecyle.ShoppingList
import com.jordantuffery.henrypottier.model.objects.retrofit.Offers
import com.jordantuffery.henrypottier.model.objects.retrofit.RetrofitBook

class ListBooksEvent(val list: List<RetrofitBook>)
class BookDetailEvent(val retrofitBook: RetrofitBook?)

class ListOffersEvent(val list: Offers?)

class ShoppingListChangeEvent(val shoppingList: ShoppingList)

class RetrofitErrorEvent(val t: Throwable)