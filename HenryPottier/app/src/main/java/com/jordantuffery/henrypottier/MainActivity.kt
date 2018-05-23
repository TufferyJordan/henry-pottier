package com.jordantuffery.henrypottier

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.jordantuffery.henrypottier.base.BaseActivity
import com.jordantuffery.henrypottier.base.BaseFragment
import com.jordantuffery.henrypottier.info.InfoFragment
import com.jordantuffery.henrypottier.library.LibraryFragment
import com.jordantuffery.henrypottier.shoppinglist.ShoppingListFragment
import kotlinx.android.synthetic.main.activity_main.main_activity_bottom_bar

class MainActivity : BaseActivity() {
    override val layoutRes: Int = R.layout.activity_main

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_infos -> {
                goToFragment(InfoFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_library -> {
                goToFragment(LibraryFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_shopping_list -> {
                goToFragment(ShoppingListFragment.newInstance())
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
        outState.putInt(EXTRA_CURRENT_NAVIGATION_ID, main_activity_bottom_bar.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        main_activity_bottom_bar.selectedItemId = savedInstanceState.getInt(
            EXTRA_CURRENT_NAVIGATION_ID)
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun goToFragment(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commitAllowingStateLoss()
    }

    companion object {
        const val REQUEST_CODE_BOOK_DETAIL_ACTIVITY = 0

        private const val EXTRA_CURRENT_NAVIGATION_ID = "EXTRA_CURRENT_NAVIGATION_ID"
    }
}
