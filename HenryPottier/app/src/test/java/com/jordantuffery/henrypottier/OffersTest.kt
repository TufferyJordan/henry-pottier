package com.jordantuffery.henrypottier

import com.jordantuffery.henrypottier.restapi.Offer
import com.jordantuffery.henrypottier.restapi.OfferType
import com.jordantuffery.henrypottier.restapi.Offers
import junit.framework.Assert
import org.junit.Test

class OffersTest {
    @Test
    fun `test if offer minus is good`() {
        var offers = Offers(listOf(Offer(OfferType.MINUS, null, 10)))
        Assert.assertEquals(5f, offers.applyOffers(15f), 0.01f)

        offers = Offers(listOf(Offer(OfferType.MINUS, null, 10)))
        Assert.assertEquals(0f, offers.applyOffers(0f), 0.01f)
    }

    @Test
    fun `test if offer percentage is good`() {
        var offers = Offers(listOf(Offer(OfferType.PERCENTAGE, null, 15)))
        Assert.assertEquals(85f, offers.applyOffers(100f), 0.01f)

        offers = Offers(listOf(Offer(OfferType.PERCENTAGE, null, 15)))
        Assert.assertEquals(0f, offers.applyOffers(0f), 0.01f)
    }

    @Test
    fun `test if offer slice is good`() {
        var offers = Offers(listOf(Offer(OfferType.SLICE, 100, 10)))
        Assert.assertEquals(105f, offers.applyOffers(115f), 0.01f)

        offers = Offers(listOf(Offer(OfferType.SLICE, 100, 10)))
        Assert.assertEquals(180f, offers.applyOffers(200f), 0.01f)

        offers = Offers(listOf(Offer(OfferType.SLICE, 100, 10)))
        Assert.assertEquals(85f, offers.applyOffers(85f), 0.01f)
    }

    @Test
    fun `test if offer minus and percentage is good`() {
        val offers = Offers(listOf(Offer(OfferType.MINUS, null, 10),
                                   Offer(OfferType.PERCENTAGE, null, 10)))
        Assert.assertEquals(80f, offers.applyOffers(100f), 0.01f)
    }

    @Test
    fun `test if offer minus and slice is good`() {
        val offers = Offers(listOf(Offer(OfferType.SLICE, 100, 10),
                                   Offer(OfferType.MINUS, null, 10)))
        Assert.assertEquals(80f, offers.applyOffers(100f), 0.01f)
    }

    @Test
    fun `test if offer percentage and slice is good`() {
        val offers = Offers(listOf(Offer(OfferType.SLICE, 100, 10),
                                   Offer(OfferType.PERCENTAGE, null, 10)))
        Assert.assertEquals(80f, offers.applyOffers(100f), 0.01f)
    }
}