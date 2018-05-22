package com.jordantuffery.henrypottier.shoppinglist

import com.jordantuffery.henrypottier.restapi.Book


class ShoppingList(var listener: OnShoppingListChange? = null) : ArrayList<ShoppingList.ShoppingItem>() {

    fun addToShoppingList(itemToAdd: Book) {
        if (map { it.item.isbn }.contains(itemToAdd.isbn)) {
            forEach {
                if (it.item.isbn == itemToAdd.isbn) it.number++
            }
        } else {
            add(ShoppingItem(itemToAdd, 1))
        }
        listener?.onShoppingListChange(this)
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
        listener?.onShoppingListChange(this)
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

    interface OnShoppingListChange {
        fun onShoppingListChange(newList: ShoppingList)
    }

    class ShoppingItem(val item: Book, var number: Int)
}