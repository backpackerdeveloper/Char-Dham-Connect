package com.shubhamtripz.chardhamconnect

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val webView: WebView = findViewById(R.id.webView)

        val url = intent.getStringExtra("URL")
        url?.let {
            webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled
            webView.settings.domStorageEnabled
            webView.settings.allowContentAccess
            webView.settings.builtInZoomControls
            webView.settings.useWideViewPort
            webView.loadUrl(it)
            webView.webViewClient = object : WebViewClient() {

                val progrssBar = findViewById<LottieAnimationView>(R.id.progrssBar)

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    progrssBar.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progrssBar.visibility = View.GONE
                    super.onPageFinished(view, url)
                }

            }
        }

        findViewById<View>(R.id.back_btn_webview).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}