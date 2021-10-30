package com.example.musicplayerapp.data

import com.example.musicplayerapp.listener.ResponseResultListener
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Request 전송
 * Retrofit 통신 및 응답 받는
 */
class RequestMusicData {
    var mResultListener: ResponseResultListener<MusicEntity>

    constructor(resultListener: ResponseResultListener<MusicEntity>) {
        mResultListener = resultListener
        execute()
    }

    // 콜백 함수 구현함으로써 비동기적으로 응답 받는다.
    fun execute() {
        RetrofitClient.service.getMusicInfo().enqueue(object : Callback<MusicEntity> {
            override fun onResponse(call: Call<MusicEntity>, response: Response<MusicEntity>) {
                if (response.isSuccessful) {
                    response.body()?.let { mResultListener.onSuccess(it) }
                } else {
                    mResultListener.onFail()
                }
            }

            override fun onFailure(call: Call<MusicEntity>, t: Throwable) {
                mResultListener.onFail()
            }

        })
    }

    object RetrofitClient {
        val service: MusicAPI = initService()
        private fun initService(): MusicAPI {

            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {

                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val sslSocketFactory = sslContext.socketFactory

            val okHttpClient = OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { hostname, session -> true }
                .addInterceptor(HeaderInterception()).build()

            return Retrofit.Builder()
                .baseUrl("https://grepp-programmers-challenges.s3.ap-northeast-2.amazonaws.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MusicAPI::class.java)


        }

    }
}