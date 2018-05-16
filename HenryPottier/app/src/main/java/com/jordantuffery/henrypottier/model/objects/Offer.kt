package com.jordantuffery.henrypottier.model.objects

import com.google.gson.annotations.SerializedName

data class Offer(val type: OfferType,
                 val value: Int)

enum class OfferType {
    @SerializedName("percentage")
    PERCENTAGE,
    @SerializedName("minus")
    MINUS,
    @SerializedName("slice")
    SLICE
}