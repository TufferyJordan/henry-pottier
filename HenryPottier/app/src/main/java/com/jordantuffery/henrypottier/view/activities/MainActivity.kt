package com.jordantuffery.henrypottier.view.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.view.base.BaseActivity
import com.jordantuffery.henrypottier.view.fragments.InfoFragment
import com.jordantuffery.henrypottier.view.fragments.LibraryFragment
import com.jordantuffery.henrypottier.view.fragments.ShoppingListFragment
import kotlinx.android.synthetic.main.activity_main.main_activity_bottom_bar

class MainActivity : BaseActivity() {
    override val layoutRes: Int = R.layout.activity_main

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
        main_activity_bottom_bar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        main_activity_bottom_bar.selectedItemId = R.id.navigation_library
    }

    private fun goToInfo() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, InfoFragment.newInstance())
            .commit()
    }

    private fun goToLibrary() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, LibraryFragment.newInstance())
            .commit()
    }

    private fun goToShoppingList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, ShoppingListFragment.newInstance())
            .commit()
    }
}
