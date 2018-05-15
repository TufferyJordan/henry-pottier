package com.jordantuffery.henrypottier.model

import com.jordantuffery.henrypottier.model.objects.Book

class ListBooksEvent(val list: List<Book>)
class BookDetailEvent(val book: Book?)

class RetrofitErrorEvent(val t: Throwable)