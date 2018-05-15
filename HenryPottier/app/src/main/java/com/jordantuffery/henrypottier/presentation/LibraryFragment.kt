package com.jordantuffery.henrypottier.presentation

import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.presentation.base.BaseFragment

class LibraryFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_library

    companion object {
        fun newInstance(): LibraryFragment {
            return LibraryFragment()
        }
    }
}