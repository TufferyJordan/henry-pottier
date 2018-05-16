package com.jordantuffery.henrypottier.view.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.RestApiService
import com.jordantuffery.henrypottier.view.LibraryAdapter
import com.jordantuffery.henrypottier.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_library.library_recycler_view

class LibraryFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_library

    private val adapter: LibraryAdapter = LibraryAdapter(ArrayList(0))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        library_recycler_view.adapter = adapter
        library_recycler_view.layoutManager = LinearLayoutManager(context)
    }

    override fun onRestApiServiceConnected(restApiService: RestApiService) {
        super.onRestApiServiceConnected(restApiService)
        restApiService.requestBooks {
            adapter.itemList = it
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance(): LibraryFragment {
            return LibraryFragment()
        }
    }
}