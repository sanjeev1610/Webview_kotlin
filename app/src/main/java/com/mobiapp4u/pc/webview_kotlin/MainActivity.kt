package com.mobiapp4u.pc.webview_kotlin

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait its loading.....")

        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.addJavascriptInterface(this@MainActivity,"hello")
        webView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressDialog.show()
                super.onPageStarted(view, url, favicon)
            }
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view!!.loadUrl(request!!.url.toString())
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressDialog.dismiss()
                super.onPageFinished(view, url)
            }

            override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                return super.shouldOverrideKeyEvent(view, event)
            }
        }
        webView.webChromeClient = object :WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
            }
        }


        button_search.setOnClickListener {
            webView.loadUrl(editText_url.text.toString())
            closeKeyBoard()
        }
        button_fb.setOnClickListener {
            webView.loadUrl("https://www.facebook.com/")
            closeKeyBoard()

        }
        button_utube.setOnClickListener {
            webView.loadUrl("https://www.youtube.com/")
            closeKeyBoard()

        }
        button_insta.setOnClickListener {
            webView.loadUrl("https://www.instagram.com/")
            closeKeyBoard()

        }
        button_html.setOnClickListener {
            webView.loadUrl("file:///android_asset/login.html")
        }
    }
    private fun closeKeyBoard(){
        val view = this.currentFocus
        if(view!=null){
            val imm:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)

        }
    }
    @JavascriptInterface
    fun displayMsg(email:String,pass:String){
         val dialog = AlertDialog.Builder(this)
        dialog.setTitle("User Info")
        dialog.setIcon(R.drawable.ic_android_black_24dp)
        dialog.setMessage("Email is : $email \n Password is : $pass" )
        dialog.setNegativeButton("OK") { di:DialogInterface, which ->
            di.dismiss()
        }
        dialog.show()
    }
}
