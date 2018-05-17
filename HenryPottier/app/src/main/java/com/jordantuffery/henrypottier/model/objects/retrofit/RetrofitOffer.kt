package com.jordantuffery.henrypottier.model.objects.retrofit

import com.google.gson.annotations.SerializedName

data class Offer(val type: OfferType,
                 val sliceValue: Int?,
                 val value: Int)

enum class OfferType {
    @SerializedName("percentage")
    PERCENTAGE,
    @SerializedName("minus")
    MINUS,
    @SerializedName("slice")
    SLICE
}