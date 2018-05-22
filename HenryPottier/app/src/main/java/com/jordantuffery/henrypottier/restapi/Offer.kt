package com.jordantuffery.henrypottier.restapi

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Offers(@SerializedName("offers") val offers: List<Offer>) {
    fun applyOffers(sum: Float?): Float {
        if (sum == null) return 0f
        var result = sum
        for (offer in offers) {
            when (offer.type) {
                OfferType.PERCENTAGE -> {
                    result *= 1 - offer.value / 100f
                }
                OfferType.MINUS -> {
                    result -= offer.value
                }
                OfferType.SLICE -> {
                    if (offer.sliceValue != null) {
                        val numberOfValue = sum.toInt() / offer.sliceValue
                        for (i in 0 until numberOfValue) {
                            result -= offer.value
                        }
                    }
                }
            }
        }
        return result
    }
}

class Offer(@Expose val type: OfferType,
            @Expose val sliceValue: Int?,
            @Expose val value: Int)

enum class OfferType {
    @SerializedName("percentage")
    PERCENTAGE,
    @SerializedName("minus")
    MINUS,
    @SerializedName("slice")
    SLICE
}