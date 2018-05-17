package com.jordantuffery.henrypottier.model.objects.retrofit

open class RetrofitBook(val isbn: String,
                        val title: String,
                        val price: Int,
                        val cover: String,
                        val synopsis: Array<String>)