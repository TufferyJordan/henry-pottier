package com.jordantuffery.henrypottier.shoppinglist

import com.jordantuffery.henrypottier.restapi.Book
import com.jordantuffery.henrypottier.utils.ShoppingListChangeEvent
import org.greenrobot.eventbus.EventBus

class ShoppingList(initializer: Array<Book?>? = null) : ArrayList<ShoppingList.ShoppingItem>() {
    init {
        if(initializer != null) {
            for (book in initializer) {
                if(book != null) {
                    addToShoppingList(book)
                }
            }
        }
    }

    fun addToShoppingList(itemToAdd: Book) {
        if (map { it.item.isbn }.contains(itemToAdd.isbn)) {
            forEach {
                if (it.item.isbn == itemToAdd.isbn) it.number++
            }
        } else {
            add(ShoppingItem(itemToAdd, 1))
        }
        EventBus.getDefault().post(ShoppingListChangeEvent(this))
    }

    @Synchronized
    fun removeFromShoppingList(itemToRemove: Book) {
        if (map { it.item.isbn }.contains(itemToRemove.isbn)) {
            var indexToRemove = -1
            for (index in 0 until size) {
                if (this[index].item.isbn == itemToRemove.isbn) {
                    if (this[index].number == 1) {
                        indexToRemove = index
                    } else {
                        this[index].number--
                    }
                }
            }
            if (indexToRemove != -1) {
                removeAt(indexToRemove)
            }
        }
        EventBus.getDefault().post(ShoppingListChangeEvent(this))
    }

    fun toLitteralList(): ArrayList<Book> {
        val realList: ArrayList<Book> = arrayListOf()
        for (shoppingItem in this) {
            for (i in 0 until shoppingItem.number) {
                realList.add(shoppingItem.item)
            }
        }
        return realList
    }

    fun sum(): Float {
        var sum: Float = 0f
        forEach {
            for (i in 0 until it.number)
                sum += it.item.price
        }
        return sum
    }


    class ShoppingItem(val item: Book, var number: Int)
}