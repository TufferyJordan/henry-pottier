package com.jordantuffery.henrypottier.restapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RestInterface {
    @GET("books")
    fun getBooks(): Call<List<Book>>

    @GET("books/{isbn}/commercialOffers")
    fun getOffers(@Path("isbn") isbn: String): Call<Offers>
}