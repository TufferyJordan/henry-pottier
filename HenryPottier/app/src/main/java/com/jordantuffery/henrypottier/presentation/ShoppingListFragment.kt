package com.jordantuffery.henrypottier.presentation

import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.presentation.base.BaseFragment

class ShoppingListFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_shopping_list

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }
}