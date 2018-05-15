package com.jordantuffery.henrypottier.presentation

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.jordantuffery.henrypottier.R
import kotlinx.android.synthetic.main.activity_main.main_activity_bottom_bar

class MainActivity : AppCompatActivity() {
    private val infoFragment = InfoFragment.newInstance()
    private val libraryFragment = LibraryFragment.newInstance()
    private val shoppingListFragment = ShoppingListFragment.newInstance()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_infos -> {
                goToInfo()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_library -> {
                goToLibrary()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_shopping_list -> {
                goToShoppingList()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_activity_bottom_bar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        main_activity_bottom_bar.selectedItemId = R.id.navigation_library
    }

    private fun goToInfo() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, infoFragment)
            .commit()
    }

    private fun goToLibrary() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, libraryFragment)
            .commit()
    }

    private fun goToShoppingList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, shoppingListFragment)
            .commit()
    }
}
