package com.freemusic.webview

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.freemusic.R
import com.freemusic.base.BaseFragment
import kotlinx.android.synthetic.main.webview_fragment.*

class WebViewFragment : BaseFragment() {
    var webView: WebView? = null
    var webUrl: String? = null
    var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.webview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = view.findViewById(R.id.webView)
        progressBar = view.findViewById(R.id.progressbar)
        fab.setOnClickListener {
            if (webView!!.canGoBack()) {
                webView!!.goBack()
                if (webView!!.goBack() != null) {
                    progressBar!!.visibility = View.VISIBLE
                }
            } else {
                activity!!.onBackPressed()
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        webLoad(webUrl)
        progressBar!!.progressDrawable.setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN
        )
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webLoad(Url: String?) {
        try {
            val webSettings = webView!!.settings
            webView!!.settings.javaScriptEnabled = true
            webView!!.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            webView!!.settings.setAppCacheEnabled(true)
            webView!!.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webSettings.domStorageEnabled = true
            webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            webSettings.useWideViewPort = true
            webView!!.webChromeClient = MyChromeClient()
            webView!!.webViewClient = MyWebViewClient()
            webView!!.loadUrl(Url)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private inner class MyChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressBar!!.progress = newProgress
            if (newProgress == 100) {
                progressBar!!.visibility = View.GONE
            }
        }
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            progressBar!!.visibility = View.VISIBLE
            view.loadUrl(url)
            return true
        }
    }

    override fun onStart() {
        super.onStart()
        setFullScreenEnable()
    }

    override fun onStop() {
        super.onStop()
        setFullScreenDisable()
    }
}
