package com.jordantuffery.henrypottier.view.fragments

import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.view.base.BaseFragment

class InfoFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_info

    companion object {
        fun newInstance(): InfoFragment {
            return InfoFragment()
        }
    }
}