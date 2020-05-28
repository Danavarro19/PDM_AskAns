package com.dnavarro.askanswerviews.retrofit

import okhttp3.Cookie
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy

object serviceLoginResponse {
//    USE YOUR URL CHANGE IT
    private const val URL: String = "http://192.168.1.8:3001/movil/"


    private val cookie: CookieHandler = CookieManager().apply {
        setCookiePolicy(CookiePolicy.ACCEPT_ALL)
    }
    private val intercep = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().addNetworkInterceptor(intercep).cookieJar(JavaNetCookieJar(cookie)).build()






    private val retrofit = Retrofit.Builder()

        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()


    fun<T> buildService(Service: Class<T>): T {
        return retrofit.create(Service)
    }
}