package com.jordantuffery.henrypottier.presentation

import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.presentation.base.BaseFragment

class InfoFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_info

    companion object {
        fun newInstance(): InfoFragment {
            return InfoFragment()
        }
    }
}