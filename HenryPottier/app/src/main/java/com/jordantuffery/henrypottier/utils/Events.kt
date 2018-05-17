package com.jordantuffery.henrypottier.utils

import com.jordantuffery.henrypottier.model.objects.retrofit.RetrofitBook

class ListBooksEvent(val list: List<RetrofitBook>)
class BookDetailEvent(val retrofitBook: RetrofitBook?)

class RetrofitErrorEvent(val t: Throwable)