package com.jordantuffery.henrypottier.library

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.jordantuffery.henrypottier.BookDetailsActivity
import com.jordantuffery.henrypottier.MainActivity
import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.base.BaseFragment
import com.jordantuffery.henrypottier.restapi.Book
import kotlinx.android.synthetic.main.fragment_library.library_recycler_view
import kotlinx.android.synthetic.main.fragment_library.library_swipe_layout

class LibraryFragment : BaseFragment(), LibraryAdapter.OnItemClickListener {

    override val layoutRes: Int = R.layout.fragment_library

    private val adapter: LibraryAdapter = LibraryAdapter(
        ArrayList(0)).apply { listener = this@LibraryFragment }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        library_recycler_view.adapter = adapter
        library_recycler_view.layoutManager = GridLayoutManager(context, 2)

        library_swipe_layout.setOnRefreshListener {
            library_swipe_layout.isRefreshing = true
            requestBooks()
        }
    }

    override fun onStart() {
        super.onStart()
        requestBooks()
    }

    private fun requestBooks() {
        presenter?.requestBooks {
            adapter.itemList = it
            adapter.notifyDataSetChanged()
            if (library_swipe_layout != null && library_swipe_layout.isRefreshing) {
                library_swipe_layout.isRefreshing = false
            }
        }
    }

    override fun onItemClick(view: View, book: Book) {
        val intent = Intent(context, BookDetailsActivity::class.java)
        intent.putExtra(BookDetailsActivity.EXTRA_BOOK_ISBN, book.isbn)
        activity?.startActivityForResult(intent,
                                         MainActivity.REQUEST_CODE_BOOK_DETAIL_ACTIVITY)
    }

    companion object {
        fun newInstance(): LibraryFragment {
            return LibraryFragment()
        }
    }
}