package com.jordantuffery.henrypottier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.View
import com.jordantuffery.henrypottier.base.BaseActivity
import com.jordantuffery.henrypottier.base.BaseFragment
import com.jordantuffery.henrypottier.info.InfoFragment
import com.jordantuffery.henrypottier.library.LibraryFragment
import com.jordantuffery.henrypottier.shoppinglist.ShoppingListFragment
import com.jordantuffery.henrypottier.utils.RetrofitErrorEvent
import kotlinx.android.synthetic.main.activity_main.main_activity_bottom_bar
import kotlinx.android.synthetic.main.activity_main.main_activity_no_connection_image
import kotlinx.android.synthetic.main.activity_main.main_activity_no_connection_layout
import kotlinx.android.synthetic.main.activity_main.main_fragment_container
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber

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

    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action) {
                ConnectivityManager.CONNECTIVITY_ACTION, WifiManager.NETWORK_STATE_CHANGED_ACTION-> {
                    val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                    updateUI(activeNetwork?.isConnectedOrConnecting)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main_activity_bottom_bar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        main_activity_bottom_bar.selectedItemId = R.id.navigation_library
        registerReceiver(receiver, IntentFilter().apply {
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        })
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
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

    @Subscribe
    fun onEvent(event: RetrofitErrorEvent) {
        updateUI(false)
    }

    private fun updateUI(withConnection: Boolean?) {
        if(withConnection == null) return
        if(withConnection) {
            main_activity_bottom_bar.selectedItemId = main_activity_bottom_bar.selectedItemId
            main_fragment_container.visibility = View.VISIBLE
            main_activity_bottom_bar.isEnabled = true
            main_activity_no_connection_layout.visibility = View.GONE
        } else {
            main_fragment_container.visibility = View.GONE
            main_activity_bottom_bar.isEnabled = false
            main_activity_no_connection_layout.visibility = View.VISIBLE
            main_activity_no_connection_image.setOnClickListener {
                presenter?.requestBooks {
                    updateUI(true)
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_BOOK_DETAIL_ACTIVITY = 0

        private const val EXTRA_CURRENT_NAVIGATION_ID = "EXTRA_CURRENT_NAVIGATION_ID"
    }
}
