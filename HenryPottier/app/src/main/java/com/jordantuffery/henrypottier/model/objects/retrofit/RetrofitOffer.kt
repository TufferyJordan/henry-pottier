package com.jordantuffery.henrypottier.model.objects.retrofit

data class Offer(val type: OfferType,
                 val sliceValue: Int?,
                 val value: Int)

enum class OfferType {
    PERCENTAGE,
    MINUS,
    SLICE
}