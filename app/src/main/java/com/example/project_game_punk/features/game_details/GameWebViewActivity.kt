package com.example.project_game_punk.features.game_details

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.project_game_punk.R
import com.google.android.material.progressindicator.CircularProgressIndicator

class GameWebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_web_view)
        val myWebView: WebView = findViewById(R.id.webview)
        val progressIndicatorLayout: ConstraintLayout = findViewById(R.id.progressIndicatorLayout)
        val progressIndicator: CircularProgressIndicator = findViewById(R.id.progressIndicator)
//        progressIndicator.
        myWebView.settings.javaScriptEnabled = true
        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                view.loadUrl(url!!)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
//                progressIndicatorLayout.visibility = View.GONE
            }
        }

        intent.getStringExtra(URL_INTENT_EXTRA)?.let { url ->


//            val encodedHtml = Base64.encodeToString(url.toByteArray(), Base64.NO_PADDING)
//            myWebView.loadData(encodedHtml, "text/html", "base64")

            myWebView.loadUrl(url)
        }
    }

    companion object {
        const val URL_INTENT_EXTRA = "intent_extra_url"
    }

}

