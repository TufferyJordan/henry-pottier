package com.jordantuffery.henrypottier

import com.jordantuffery.henrypottier.restapi.Book
import com.jordantuffery.henrypottier.shoppinglist.ShoppingList
import junit.framework.Assert
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class DataRequestPresenterTest {
    private val presenterToTest: DataRequestPresenter = DataRequestPresenterImpl()

    @Test
    fun `When I request a book, then i receive books well parsed`() {
        val latch = CountDownLatch(1)
        presenterToTest.requestBooks {
            Assert.assertTrue(it.isNotEmpty())
            latch.countDown()
        }
        Assert.assertTrue(latch.await(5000, TimeUnit.MILLISECONDS))
    }

    @Test
    fun `When I request a book, then i don't receive the bad book`() {
        val latch = CountDownLatch(1)
        presenterToTest.requestBooks {
            Assert.assertTrue(it.isNotEmpty())
            val bookToNotRequest = it[1]
            presenterToTest.requestBookDetails(it[0].isbn) {
                Assert.assertTrue(bookToNotRequest.isbn != it?.isbn)
                latch.countDown()
            }
            Assert.assertTrue(latch.await(5000, TimeUnit.MILLISECONDS))
        }
        Assert.assertTrue(latch.await(5000, TimeUnit.MILLISECONDS))
    }

    @Test
    fun `Given I pick a book, when I'm looking for the offers, then I get all the offers`() {
        var latch = CountDownLatch(1)
        var bookToRequest: Book? = null
        presenterToTest.requestBooks {
            presenterToTest.requestBookDetails(it[0].isbn) {
                bookToRequest = it
                latch.countDown()
            }
            Assert.assertTrue(latch.await(5000, TimeUnit.MILLISECONDS))
        }
        Assert.assertTrue(latch.await(5000, TimeUnit.MILLISECONDS))

        latch = CountDownLatch(1)
        presenterToTest.requestOffers(ShoppingList(arrayOf(bookToRequest))) {
            Assert.assertTrue(it.offers.isNotEmpty())
            latch.countDown()
        }
        Assert.assertTrue(latch.await(5000, TimeUnit.MILLISECONDS))
    }
}