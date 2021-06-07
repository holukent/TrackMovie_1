package com.chinlung.trackmovie.retrofit

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class MyVolleyStringRequest(
    method: Int,
    url: String?,
    listener: Response.Listener<String>?,
    errorListener: Response.ErrorListener?
) : StringRequest(method, url, listener, errorListener) {

    override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
        val parsed = try {
            String(response!!.data, Charset.defaultCharset())
        } catch (e: UnsupportedEncodingException) {
            String(response!!.data)
        }
        return Response.success(parsed,HttpHeaderParser.parseCacheHeaders(response))
    }
}