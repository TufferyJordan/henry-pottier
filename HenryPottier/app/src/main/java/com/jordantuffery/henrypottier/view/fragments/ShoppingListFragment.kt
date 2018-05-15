package com.jordantuffery.henrypottier.view.fragments

import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.view.base.BaseFragment

class ShoppingListFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_shopping_list

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }

}