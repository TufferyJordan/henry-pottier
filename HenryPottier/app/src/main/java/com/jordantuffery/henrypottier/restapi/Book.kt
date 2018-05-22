package com.jordantuffery.henrypottier.restapi

open class Book(val isbn: String,
                val title: String,
                val price: Int,
                val cover: String,
                val synopsis: Array<String>)