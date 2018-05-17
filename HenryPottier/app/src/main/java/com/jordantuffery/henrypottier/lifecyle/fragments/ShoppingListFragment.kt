package com.jordantuffery.henrypottier.lifecyle.fragments

import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.lifecyle.DataRequestService
import com.jordantuffery.henrypottier.lifecyle.base.BaseFragment

class ShoppingListFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_shopping_list


    override fun onDataRequestServiceConnected(dataRequestService: DataRequestService) {
        super.onDataRequestServiceConnected(dataRequestService)
    }

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }
}