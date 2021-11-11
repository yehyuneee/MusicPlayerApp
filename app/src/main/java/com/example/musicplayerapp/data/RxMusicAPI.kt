package com.example.musicplayerapp.data

import android.database.Observable
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Rxjava 구조로 리팩토링하는 중
 * Retrofit API Interface 정의
 * https://hwanine.github.io/android/MVVMFinal/ 참고
 */
interface RxMusicAPI {
    @GET("/2020-flo/song.json")
    fun getMusicInfo(): io.reactivex.rxjava3.core.Observable<MusicEntity>

    // const val은 컴파일 시간에 결정되는 상수이다. 함수나 어떤 클래스의 생성자에게도 결코 할당될 수 없고 오직 문자열이나 기본 자료형으로 할당되어야 함
    // const로 선언을 하면 클래스의 프로퍼티나 지역변수로 할당할 수 없게 된다.
    // 때문에 companion object안에 상수로 선언하게 된다.
    companion object {
        private const val BASE_URL =
            "https://grepp-programmers-challenges.s3.ap-northeast-2.amazonaws.com"

        fun create(): RxMusicAPI {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?,
                ) {

                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?,
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
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RxMusicAPI::class.java)
        }
    }
}