package com.jordantuffery.henrypottier.lifecyle.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.lifecyle.base.BaseActivity
import com.jordantuffery.henrypottier.lifecyle.base.BaseFragment
import com.jordantuffery.henrypottier.lifecyle.fragments.InfoFragment
import com.jordantuffery.henrypottier.lifecyle.fragments.LibraryFragment
import com.jordantuffery.henrypottier.lifecyle.fragments.ShoppingListFragment
import kotlinx.android.synthetic.main.activity_main.main_activity_bottom_bar

class MainActivity : BaseActivity() {
    override val layoutRes: Int = R.layout.activity_main

    private val infoFragment = InfoFragment.newInstance()
    private val libraryFragment = LibraryFragment.newInstance()
    private val shoppingListFragment = ShoppingListFragment.newInstance()

    private var currentFragmentId: Int? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_infos -> {
                goToFragment(infoFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_library -> {
                goToFragment(libraryFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_shopping_list -> {
                goToFragment(shoppingListFragment)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_BOOK_DETAIL_ACTIVITY) {
            when (resultCode) {
                BookDetailsActivity.RESULT_CODE_ADDED_ITEM_IN_LIST -> main_activity_bottom_bar.selectedItemId = R.id.navigation_shopping_list
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(EXTRA_CURRENT_FRAGMENT_ID, currentFragmentId!!)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        restoreCurrentFragment(savedInstanceState.getInt(EXTRA_CURRENT_FRAGMENT_ID))
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun goToFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commitAllowingStateLoss()
        currentFragmentId = getCurrentFragmentId(fragment)
    }

    private fun getCurrentFragmentId(fragment: BaseFragment): Int = when (fragment) {
        infoFragment -> ID_FRAGMENT_INFO
        libraryFragment -> ID_FRAGMENT_LIBRARY
        else -> ID_FRAGMENT_SHOPPING_LIST
    }

    private fun restoreCurrentFragment(id: Int) {
        when (id) {
            ID_FRAGMENT_INFO -> main_activity_bottom_bar.selectedItemId = R.id.navigation_infos
            ID_FRAGMENT_LIBRARY -> main_activity_bottom_bar.selectedItemId = R.id.navigation_library
            else -> main_activity_bottom_bar.selectedItemId = R.id.navigation_shopping_list
        }
    }

    companion object {
        const val REQUEST_CODE_BOOK_DETAIL_ACTIVITY = 0

        private const val EXTRA_CURRENT_FRAGMENT_ID = "EXTRA_CURRENT_FRAGMENT_ID"

        private const val ID_FRAGMENT_INFO = 0
        private const val ID_FRAGMENT_LIBRARY = 1
        private const val ID_FRAGMENT_SHOPPING_LIST = 2
    }
}
