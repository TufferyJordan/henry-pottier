package com.jordantuffery.henrypottier.model

data class Offer(val type: OfferType,
                 val value: Int)

enum class OfferType {
    PERCENTAGE,
    MINUS,
    SLICE
}