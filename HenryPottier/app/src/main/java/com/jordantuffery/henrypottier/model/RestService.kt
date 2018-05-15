package com.jordantuffery.henrypottier.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RestService {
    @GET("books")
    fun getBooks(): Call<List<Book>>

    @GET("books/{isbn}")
    fun getOffers(@Path("isbn") isbn: String): Call<List<Offer>>

    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://henri-potier.xebia.fr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestService::class.java)!!
    }
}