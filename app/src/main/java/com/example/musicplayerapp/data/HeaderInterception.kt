package com.example.musicplayerapp.data

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor 정의
 */
class HeaderInterception : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val request = origin.newBuilder()
            .header("accept", "application/json; charset=UTF-8")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()

        return chain.proceed(request)
    }
}