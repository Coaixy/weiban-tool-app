package com.github.coaixy.weiban_tool

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class Js(baseContext: Context,data:Data) {
    private lateinit var context:Context
    private lateinit var data:Data
    init {
        this.context = baseContext
        this.data = data
    }
    @JavascriptInterface
    fun test(){
        Toast.makeText(context, "this is test method", Toast.LENGTH_SHORT).show()
    }
    @JavascriptInterface
    fun x(x:String){
        this.data.x_token=x
    }
    @JavascriptInterface
    fun send(body:String){
        val dataList = body.split("&")
        for (i in dataList){
            val dataList2 = i.split("=")
            when(dataList2[0]){
                "userId"->{
                    data.userId=dataList2[1]
                }
                "tenantCode"->{
                    data.tenantCode=dataList2[1]
                }
                "userProjectId"->{
                    data.userProjectId=dataList2[1]
                }
            }
        }
    }
}