package com.jordantuffery.henrypottier.model

import com.jordantuffery.henrypottier.model.objects.Book
import com.jordantuffery.henrypottier.model.objects.Offer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RestInterface {
    @GET("books")
    fun getBooks(): Call<List<Book>>

    @GET("books/{isbn}")
    fun getOffers(@Path("isbn") isbn: String): Call<List<Offer>>
}