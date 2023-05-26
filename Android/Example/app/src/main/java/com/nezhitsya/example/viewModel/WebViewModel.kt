package com.nezhitsya.example.viewModel

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Message
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.nezhitsya.example.base.BaseViewModel
import com.nezhitsya.example.base.SingleLiveEvent

class WebViewModel: BaseViewModel() {

    val isPagedMoved = SingleLiveEvent<Void>()
    val isFirstPageLoadFinished = SingleLiveEvent<Void>()
    val isError = SingleLiveEvent<Int>().apply { value = 0 }
    val isRefresh = SingleLiveEvent<Boolean>().apply { value = false }
    var pageLoaded = true
    var dialog: Dialog? = null

    @SuppressLint("StaticFieldLeak")
    private var context: Context? = null

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView(context: Context, webView: WebView) {
        this.context = context

        webView.clearCache(true)
        webView.settings.textZoom = 100

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportMultipleWindows(true)
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.loadWithOverviewMode = true

        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false

        webView.webViewClient = webclient
        webView.webChromeClient = chromeClient
    }

    private val webclient = object : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            CookieManager.getInstance().flush()
            isPagedMoved.call()
            isFirstPageLoadFinished.call()

            if (pageLoaded) {
                isRefresh.postValue(false)
                showProgress.value = false
            } else {
                isRefresh.postValue(true)
                showProgress.value = true
            }

            pageLoaded = false
            showProgress.value = false
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)

            error?.run {
                when (errorCode) {
                    ERROR_CONNECT,
                    ERROR_TIMEOUT,
                    ERROR_HOST_LOOKUP -> {
                        isError.value = errorCode
                        pageLoaded = false
                    }
                }
            }
        }

    }

    private val chromeClient = object : WebChromeClient() {

        override fun onCloseWindow(window: WebView?) {
            super.onCloseWindow(window)
            window?.destroy()
            dialog?.dismiss()
        }

        @SuppressLint("SetJavaScriptEnabled")
        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            val webView = WebView(context!!)

            webView.settings.run {
                javaScriptEnabled = true
                domStorageEnabled = true
                setSupportMultipleWindows(true)
                javaScriptCanOpenWindowsAutomatically = true
                loadWithOverviewMode = true

                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
            }

            dialog = Dialog(context!!).apply {
                setContentView(webView)

                val params = window?.attributes?.apply {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }
                window?.attributes = params
            }

            webView.webViewClient = webclient
            webView.webChromeClient = object : WebChromeClient() {
                override fun onCloseWindow(window: WebView?) {
                    dialog?.dismiss()
                    webView.destroy()
                    window?.destroy()
                }
            }

            dialog?.setOnDismissListener {
                webView.destroy()
            }
            dialog?.show()

            (resultMsg?.obj as WebView.WebViewTransport).webView = webView
            resultMsg.sendToTarget()

            return true
        }

    }

}