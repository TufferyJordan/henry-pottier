package com.jordantuffery.henrypottier.model

data class Book(val isbn: String,
                val title: String,
                val price: Int,
                val cover: String,
                val synopsis: Array<String>)