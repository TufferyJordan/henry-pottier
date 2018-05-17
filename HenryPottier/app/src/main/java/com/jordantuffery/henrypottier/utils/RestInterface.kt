package com.jordantuffery.henrypottier.utils

import com.jordantuffery.henrypottier.model.objects.retrofit.Offer
import com.jordantuffery.henrypottier.model.objects.retrofit.RetrofitBook
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RestInterface {
    @GET("books")
    fun getBooks(): Call<List<RetrofitBook>>

    @GET("books/{isbn}")
    fun getOffers(@Path("isbn") isbn: String): Call<List<Offer>>
}