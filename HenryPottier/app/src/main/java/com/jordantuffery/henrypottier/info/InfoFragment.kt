package com.jordantuffery.henrypottier.info

import com.jordantuffery.henrypottier.R
import com.jordantuffery.henrypottier.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_info.github_link_webview

class InfoFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_info

    companion object {
        fun newInstance(): InfoFragment {
            return InfoFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        github_link_webview.loadUrl("https://github.com/TufferyJordan/henry-pottier/blob/master/README.md")
    }

    override fun onStop() {
        github_link_webview.stopLoading()
        super.onStop()
    }
}