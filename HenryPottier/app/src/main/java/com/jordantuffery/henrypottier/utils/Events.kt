package com.jordantuffery.henrypottier.utils

import com.jordantuffery.henrypottier.restapi.Offers
import com.jordantuffery.henrypottier.restapi.Book
import com.jordantuffery.henrypottier.shoppinglist.ShoppingList

class ListBooksEvent(val list: List<Book>)
class BookDetailEvent(val book: Book?)

class ListOffersEvent(val list: Offers?)

class ShoppingListChangeEvent(val shoppingList: ShoppingList)

class RetrofitErrorEvent(val t: Throwable)