package com.jordantuffery.henrypottier.lifecyle.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.lifecyle.DataRequestService
import com.jordantuffery.henrypottier.lifecyle.LibraryAdapter
import com.jordantuffery.henrypottier.lifecyle.activities.BookDetailsActivity
import com.jordantuffery.henrypottier.lifecyle.activities.MainActivity
import com.jordantuffery.henrypottier.lifecyle.base.BaseFragment
import com.jordantuffery.henrypottier.model.objects.retrofit.RetrofitBook
import kotlinx.android.synthetic.main.fragment_library.library_recycler_view
import kotlinx.android.synthetic.main.fragment_library.library_swipe_layout

class LibraryFragment : BaseFragment(), LibraryAdapter.OnItemClickListener {

    override val layoutRes: Int = R.layout.fragment_library

    private val adapter: LibraryAdapter = LibraryAdapter(ArrayList(0)).apply { listener = this@LibraryFragment }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        library_recycler_view.adapter = adapter
        library_recycler_view.layoutManager = GridLayoutManager(context, 2)

        library_swipe_layout.setOnRefreshListener {
            library_swipe_layout.isRefreshing = true
            requestBooks()
        }
    }

    override fun onDataRequestServiceConnected(dataRequestService: DataRequestService) {
        super.onDataRequestServiceConnected(dataRequestService)
        requestBooks()
    }
    private fun requestBooks() {
        dataRequestService?.requestBooks {
            adapter.itemList = it
            adapter.notifyDataSetChanged()
            if(library_swipe_layout.isRefreshing) {library_swipe_layout.isRefreshing = false}
        }
    }

    override fun onItemClick(view: View, book: RetrofitBook) {
        val intent = Intent(context, BookDetailsActivity::class.java)
        intent.putExtra(EXTRA_BOOK_ISBN, book.isbn)
        activity?.startActivityForResult(intent, MainActivity.REQUEST_CODE_BOOK_DETAIL_ACTIVITY)
    }

    companion object {
        const val EXTRA_BOOK_ISBN = "EXTRA_BOOK_ISBN"

        fun newInstance(): LibraryFragment {
            return LibraryFragment()
        }
    }
}