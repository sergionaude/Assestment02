package com.example.assestment02.rest

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class SongInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            //addHeader("term", "classic")
            //addHeader("amp;media", "music")
            // addHeader("entity", "song")
            //addHeader("amp;limit", "50")
        }.build()

        Log.d("ENVIO", request.toString())
        //Log.d("Recibo", chain.proceed(request).toString())
        return chain.proceed(request)
    }
}