package com.github.coaixy.weiban_tool

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import android.content.ClipboardManager as ClipboardManager1

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val javascript = "XMLHttpRequest.prototype.reallyOpen=XMLHttpRequest.prototype.open;XMLHttpRequest.prototype.open=function(a,b,c,d,e){this.reallyOpen(a,b,c,d,e)};XMLHttpRequest.prototype.reallySetH=XMLHttpRequest.prototype.setRequestHeader;XMLHttpRequest.prototype.setRequestHeader=function(a,b){if(a==\"X-Token\"){console.log(b);android.x(b)}this.reallySetH(a,b)};XMLHttpRequest.prototype.reallySend=XMLHttpRequest.prototype.send;XMLHttpRequest.prototype.send=function(a){android.send(a);console.log(a);this.reallySend(a)};"
        var flag2 = true
        var flag1 = true
        val data = Data("","","","")
        val webView = findViewById<WebView>(R.id.webview)
        webView.loadUrl("http://weiban.mycourse.cn/#/")
        webView.webViewClient = object : WebViewClient(){
            //禁止跳转
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (flag1){
                    if (flag2)webView.loadUrl("javascript:$javascript");flag2=false//载入拦截代码
                    if (data.tenantCode!="" && data.userId!="" && data.userProjectId!="" && data.x_token!=""){
                        dialog(data)
                        flag1 = false
                    }
                }
                super.onPageFinished(view, url)
            }
        }

        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.addJavascriptInterface(Js(baseContext,data),"android")
    }
    fun dialog(data: Data){
        val str = "userId=${data.userId}&tenantCode=${data.tenantCode}&x-token=${data.x_token}&userProjectId=${data.userProjectId}"
        AlertDialog.Builder(this)
            .setTitle("信息")
            .setMessage(str)
            .setPositiveButton("复制"){_,_->
                run {
                    val cm = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                    cm.setPrimaryClip(ClipData.newPlainText("data",str))
                }
            }
            .show()
    }
}